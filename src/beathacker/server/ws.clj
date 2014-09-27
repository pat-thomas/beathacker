(ns beathacker.server.ws
  (:require [org.httpkit.server         :as http]
            [clojure.data.json          :as json]
            [beathacker.server.app-loop :as app-loop]
            [beathacker.server.models.rhythm-seq :as rhythm-seq]
            [compojure.core             :as compojure]
            [compojure.handler          :as handler]
            [compojure.route            :as route]))

(defn handle-core-api-req
  [req]
  (let [body (-> req :body slurp (json/read-str :key-fn keyword))]
    (if-let [{:keys [handler-name data]} body]
      (do (app-loop/fire-event! handler-name data)
          (json/json-str {:status "OK"
                          :msg    "event-fired"}))
      (json/json-str {:status "InvalidRequest"}))))

(defn handle-rhythm-seq-api-req
  [req]
  (let [body (-> req :body slurp (json/read-str :key-fn keyword))]
    (do (doseq [data body]
          (apply app-loop/fire-event! (rhythm-seq/data->evt data)))
        (json/json-str {:status "ImplementMe"}))))

(compojure/defroutes app-routes
  (compojure/GET  "/ping"       [] "pong\n")
  (compojure/POST "/core"       [] #'handle-core-api-req)
  (compojure/POST "/rhythm-seq" [] #'handle-rhythm-seq-api-req))

(defn app
  []
  (let [port 8080]
    (do (println "Web server listening on port" port)
        (http/run-server (handler/site #'app-routes) {:port port}))))

(comment
  (app)
)

