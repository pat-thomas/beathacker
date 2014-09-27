(ns beathacker.app.state)

(def app-state
  (atom
   {:initialized false
    :components  {:rhythm-box {:selected-option {:columns     1
                                                 :rows        1
                                                 :repetitions 1}
                               :clicked         {}}}}))

