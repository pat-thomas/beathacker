(ns beathacker.server.models.rhythm-seq)

(defn rhythm-data->evt
  "The row represents the sound, the column represents the :wait"
  [rhythm-data]
  (let [{:keys [column row sound-type]} rhythm-data]
    [(or sound-type :kick) {:wait column}]))

(defn process-repetitions
  [{:keys [repetitions rhythm-data num-columns] :as rhythm-seq}]
  (flatten
   (for [num (map #(* % num-columns) (range repetitions))]
     (map (fn [data-point]
            (update-in data-point [:column] #(+ % num)))
          rhythm-data))))

(defn process
  [rhythm-seq]
  (->> rhythm-seq
       process-repetitions
       (map rhythm-data->evt)))
