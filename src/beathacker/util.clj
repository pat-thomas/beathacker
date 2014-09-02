(ns beathacker.util
  (:require [clojure.core.async :as async]))

(def buffer-type->buffer
  {:buffer          clojure.core.async/buffer
   :dropping-buffer clojure.core.async/dropping-buffer
   :sliding-buffer  clojure.core.async/sliding-buffer})

(defn expr->chan+buffer
	[buffer-type buffer-size]
  (if-let [buffer (get buffer-type->buffer buffer-type)]
    (list buffer buffer-size)
    (throw (java.lang.Exception. (format "Error: %s is not a valid core.async buffer type. Valid buffer types are: %s"
                                         (name buffer-type)
                                         (->> buffer-type->buffer
                                              keys
                                              sort
                                              (mapv name)
                                              (mapv symbol)))))))

(defmacro defchan
  [channel-name & [buffer-type buffer-size]]
  (if-not (and buffer-type buffer-size)
    `(def ~channel-name (async/chan))
    `(def ~channel-name (async/chan ~(expr->chan+buffer buffer-type buffer-size)))))

