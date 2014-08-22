(ns beathacker.events
  (:require [clojure.core.async :as async :refer [chan go <! >! pub sub]]
            [beathacker.util    :as util  :refer [defchan]]))

(defchan publisher)
(defchan subscriber)

(defn take-and-print
  [channel prefix]
  (async/go-loop []
    (println (str prefix ": " (<! channel)))
    (recur)))

(defn fire-event!
  [topic data]
  (go (>! publisher {:topic topic
                     :data data})))

(defn init!
  []
  (let [publication (pub publisher #(:topic %))]
    (do (sub publication :event-fired subscriber)
        (take-and-print subscriber "subscriber"))))




