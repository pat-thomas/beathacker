(ns beathacker.util
  (:require [clojure.core.async :as async]))

(def buffer-type->buffer
  {:buffer          clojure.core.async/buffer
   :dropping-buffer clojure.core.async/dropping-buffer
   :sliding-buffer  clojure.core.async/sliding-buffer})

(defmacro defchan
  [channel-name & [buffer-type buffer-size]]
  (if (and buffer-type buffer-size)
    (if-let [buffer (get buffer-type->buffer buffer-type)]
      (let [buffer-expr (list buffer buffer-size)]
       `(def ~channel-name (async/chan ~buffer-expr)))
      (throw (java.lang.Exception. (format "Error: %s is not a valid core.async buffer type. Valid buffer types are: %s"
                                           (name buffer-type)
                                           (->> buffer-type->buffer
                                                keys
                                                sort
                                                (mapv name)
                                                (mapv symbol))))))
    `(def ~channel-name (async/chan))))
