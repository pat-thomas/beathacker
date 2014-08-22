(ns beathacker.events
  (:require [clojure.core.async  :as async :refer [chan go <! >! pub sub]]
            [beathacker.util     :as util  :refer [defchan]]
            [beathacker.handlers :as handlers]))

(defchan publisher)
(defchan subscriber)

(defn fire-event!
  [topic data]
  (go (>! publisher {:topic topic
                     :data  data})))

(defn take-from-channel
  [channel prefix]
  (async/go-loop []
    (let [val-from-chan (<! channel)]
      (do (handlers/handle-event val-from-chan)
          (recur)))))

(defn init!
  []
  (let [publication (pub publisher #(:topic %))]
    (do (sub publication :event-fired subscriber)
        (take-from-channel subscriber "subscriber"))))




