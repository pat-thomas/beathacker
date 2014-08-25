(ns beathacker.util
  (:require [clojure.core.async :as async]))

(defmacro defchan
  [channel-name & [buffer-size]]
  (if buffer-size
    `(def ~channel-name (async/chan ~buffer-size))
    `(def ~channel-name (async/chan))))
