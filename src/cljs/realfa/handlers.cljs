(ns realfa.handlers
  (:require
    [realfa.db :as db]
    [re-frame.core :as re-frame]))

(re-frame/register-handler
  :initialize-db
  (fn [_ _]
    db/default-db))

(re-frame/register-handler
  :set-active-panel
  (fn [db [_ active-panel]]
    (assoc db :active-panel active-panel)))

(re-frame/register-handler
  :change-nama
  (fn [db [_ nama]]
    (assoc db :nama nama)))

(re-frame/register-handler
  :set-matrix
  (fn [db [_ year idx value]]
    (let [cells (get-in db [:matrix year])]
      (->> #(assoc % year (update-in cells [idx] (fn [n] value)))
           (update-in db [:matrix])))))