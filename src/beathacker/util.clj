(ns beathacker.util
  (:require [clojure.core.async :as async]))

(defmacro defchan
  [channel-name]
  `(def ~channel-name (async/chan)))
