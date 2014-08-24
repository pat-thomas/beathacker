(ns beathacker.events
  (:require [clojure.core.async  :as async :refer [chan go <! >! pub sub]]
            [beathacker.util     :as util  :refer [defchan]]
            [beathacker.handlers :as handlers]))

(defchan publisher)
(defchan subscriber)
(defchan log-subscriber)

(defn fire-event!
  [topic data]
  (go (>! publisher {:topic topic
                     :data  data})))

(defn take-event-from-channel
  [channel]
  (async/go-loop []
    (let [val-from-chan (<! channel)]
      (do (handlers/handle-event val-from-chan)
          (recur)))))

(defn take-event-from-channel-for-logging
  [channel]
  (async/go-loop []
    (let [val-from-chan (<! channel)]
      (do (println "event observed:" val-from-chan)
          (recur)))))

(defn init-subscribers!
  []
  (let [publication (pub publisher #(:topic %))]
    (do (sub publication :event-fired subscriber)
        (sub publication :event-fired log-subscriber)
        :subscribers-initialized)))

(defn init-event-listeners!
  []
  (do (take-event-from-channel subscriber)
      (take-event-from-channel-for-logging log-subscriber)
      :event-listeners-initialized))

(defn init!
  []
  (do (init-subscribers!)
      (init-event-listeners!)))
