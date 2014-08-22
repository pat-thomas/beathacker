(ns beathacker.core
  (:require [overtone.live      :as overtone]
            [clojure.core.async :as async :refer [chan go <! >! pub sub]]
            [beathacker.util    :as util  :refer [defchan]]))

(defchan publisher)
(defchan subscriber)

(def publication
  (pub publisher #(:topic %)))

(sub publication :event-fired subscriber)

(defn take-and-print
  [channel prefix]
  (async/go-loop []
    (println (str prefix ": " (<! channel)))
    (recur)))

(defn main
  []
  (take-and-print subscriber "subscriber"))

(comment
  (main)

  (go (>! publisher {:topic :event-fired
                     :data {:type :note
                            :val  :c3}}))
)
