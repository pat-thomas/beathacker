(ns beathacker.app-loop
  (:require [clojure.core.async :as async :refer [go <! >!]]
            [beathacker.util :refer [defchan]]))

(defchan event-channel)

(defn run-app-loop
  []
  :implement-me)
