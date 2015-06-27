(ns realfa.core
  (:require
    [reagent.core :as reagent]
    [re-frame.core :as re-frame]
    [realfa.handlers]
    [realfa.subs]
    [realfa.routes :as routes]
    [realfa.views :as views]))

(defn selid
  [id]
  (.getElementById js/document id))

(defn mount-root []
  (reagent/render [views/main-panel] (selid "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
