(ns realfa.core
  (:require
    [compojure.core :refer :all]
    [compojure.route :refer :all]
    [selmer.parser :refer [render-file]]
    [noir.session :as sess]
    [noir.response :as resp]
    [ring.middleware.defaults :refer :all]
    [org.httpkit.server :refer [run-server]]
    [ring.middleware.reload :refer [wrap-reload]]))

(defonce server (atom nil))
(def tdir "public/")

(defn homepage
  [nama]
  (render-file (str tdir "index.html")
               {:nama nama}))

(defn api-serve
  ([] (resp/json {:jojon "let the guy dance"}))
  ([nama] (resp/json {:jojon (str "Yakin ini nama lo? " nama)})))

(def all-routes
  (routes (GET "/" [] (homepage "masyarakat umum"))
          (GET "/jojon" [] (api-serve))
          (GET "/:nama" [nama] (do (sess/put! :nama nama) (api-serve nama)))
          (resources "resources")
          (not-found "Nice try dude, nothing to see here...")))

(def app (-> all-routes
             (sess/wrap-noir-session)
             (wrap-defaults site-defaults)
             (wrap-reload)))

(defn -main
  [& [port & args]]
  (let [iport (when port (int port))]
    (reset! server (run-server app {:port (or iport 3000)}))))

(defn stop [] (reset! server (@server)))