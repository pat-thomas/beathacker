(ns beathacker.app-loop
  (:require [overtone.live      :as overtone]   
            [clojure.core.async :as async :refer [go <! >!]]
            [beathacker.util    :refer [defchan]]))

(defchan event-channel)

(defn run-app-loop
  []
  :implement-me)
