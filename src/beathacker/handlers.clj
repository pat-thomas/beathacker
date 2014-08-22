(ns beathacker.handlers)

(def event-handler-registry (atom {}))

(defn register-handler!
  [evt-name handler-impl]
  (swap! event-handler-registry assoc evt-name handler-impl))

(register-handler!
 :note
 (fn [data]
   (def data data)
   :nil))

(defn handle-event
  [{:keys [topic data] :as params}]
  (when-let [handler (get @event-handler-registry (:type data))]
    (handler data)))

(comment
  
)
