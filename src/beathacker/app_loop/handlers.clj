(ns beathacker.app-loop.handlers
  (:require [overtone.live :as overtone]
            [beathacker.app-loop.handlers.registry :refer [register-handler!]]))

(def kick (overtone/sample (overtone/freesound-path 2086)))

(defn init!
  []
  (register-handler! :kick kick))


