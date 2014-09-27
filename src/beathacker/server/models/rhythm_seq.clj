(ns beathacker.server.models.rhythm-seq)

(defn data->evt
  "The row represents the sound, the column represents the :wait"
  [data]
  (let [column     (:column data)
        row        (:row data)
        sound-type (or (:sound-type data) :kick)]
    [sound-type {:wait column}]))
