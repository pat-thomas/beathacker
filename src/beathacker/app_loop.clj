(ns beathacker.app-loop
  (:require [overtone.live      :as overtone       :refer [apply-by at]]
            [clojure.core.async :as async          :refer [go <! >!]]
            [beathacker.app-loop.handlers.registry :refer [trigger-handler]]
            [beathacker.util                       :refer [defchan]]))

(defchan event-channel)

(def metro (overtone/metronome 120))

(defn fire-event!
  [handler-name & [data]]
  (let [base-msg {:handler-name handler-name}
        msg      (if-not data
                   base-msg
                   (assoc base-msg :data data))]
    (def msg msg)
    (go (>! event-channel msg))))

(defn handler
  [channel]
  (go (when-let [{:keys [handler-name data]} (<! channel)]
        (do (def chicken [handler-name data])
            (if data
              (trigger-handler handler-name data)
              (trigger-handler handler-name)))))
  :not-sure-if-this-has-to-return-anything-meaningful)

(defn run-app-loop!
  [nome cb channel]
  (let [beat (nome)]
    (at (nome beat)
        (cb channel))
    (apply-by (nome (inc beat)) #'run-app-loop! nome handler channel [])))

(comment
  (run-app-loop! metro
                 handler
                 event-channel)
  (fire-event! :kick)
  )
