(ns realfa.views
  (:require [re-frame.core :refer [subscribe dispatch]]
            [re-com.core :as re-com]))

;; --------------------
(defn home-title []
  (let [name (subscribe [:name])]
    (fn []
      [re-com/title
       :label (str "Hello from " @name ". This is the Home Page.")
       :level :level1])))

(defn link-to-about-page []
  [re-com/hyperlink-href
   :label "go to About Page"
   :href "#/about"])

(defn link-to-jojon []
  [re-com/hyperlink-href
   :label "goto jojon"
   :href "#/jojon"])

(defn home-panel []
  [re-com/v-box
   :gap "1em"
   :children [[home-title]
              [link-to-about-page]
              [link-to-jojon]]])

;; Jojon page ----------------------

(defn int-value
  [tar]
  (js/parseInt (-> tar .-target .-value)))

(defn input-text []
  (fn []
    [:div
     [:input
      {:type      "text"
       :on-change #(dispatch [:change-nama (-> % .-target .-value)])}]]))

(defn jojon-box []
  (let [nama (subscribe [:nama])]
    (fn []
      [:div
       [input-text]
       [:div [:h1 (str "Hello " @nama)]]])))

(defn jojon-matrix [matrix]
  (fn [matrix]
    [:div
     [:p (str @matrix)]
     [:table
      (for [idx (range 7)]
        [:tr (for [year (range 2015 2023)]
               [:td
                [:input
                 {:type        "text"
                  :size        "15"
                  :placeholder (aget (@matrix year) idx)
                  :on-change   #(dispatch [:set-matrix year idx (int-value %)])}]])])]]))

(defn jojon-summary
  []
  (let [matrix (subscribe [:matrix])]
    (fn []
      [:div [:table
             [:thead [:th [:h4 "Year  "]] [:th [:h4 "Valuation"]]]
             (for [con @matrix]
               [:tr
                [:td {:col 25} [:strong (key con)]]
                [:td (reduce + (into [] (val con)))]])]])))

(declare link-to-home-page)

(defn jojon-panel []
  [re-com/v-box
   :gap "1em"
   :children [[jojon-box]
              [link-to-about-page]
              [link-to-home-page]
              [jojon-matrix (subscribe [:matrix])]
              [jojon-summary]]])

;; --------------------
(defn about-title []
  [re-com/title
   :label "This is the About Page."
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink-href
   :label "go to Home Page"
   :href "#/"])

(defn about-panel []
  [re-com/v-box
   :gap "1em"
   :children [[about-title] [link-to-home-page]]])

;; --------------------
(defmulti panels identity)
(defmethod panels :home-panel [] [home-panel])
(defmethod panels :about-panel [] [about-panel])
(defmethod panels :jojon-panel [] [jojon-panel])
(defmethod panels :default [] [:div])

(defn main-panel []
  (let [active-panel (subscribe [:active-panel])]
    (fn []
      [re-com/v-box
       :height "100%"
       :children [(panels @active-panel)]])))
