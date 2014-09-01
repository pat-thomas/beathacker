(ns beathacker.util
  (:require [clojure.core.async :as async]))

(defmacro defchan
  [channel-name & [buffer-expr]]
  (if buffer-expr
    `(def ~channel-name (async/chan ~buffer-expr))
    `(def ~channel-name (async/chan))))
