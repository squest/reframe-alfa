(ns realfa.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]))

(defn value
  [target]
  (-> target .-target .-value))

;; --------------------
(defn home-title []
  (let [name (re-frame/subscribe [:name])]
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

(defn input-text []
  (fn []
    [:div
     [:input {:type      "text"
              :on-change #(re-frame/dispatch [:change-nama (value %)])}]]))

(defn jojon-box []
  (let [nama (re-frame/subscribe [:nama])]
    (fn []
      [:div
       [input-text]
       [:div [:h1 (str "Hello " (let [[a b] @nama] b))]]])))

(declare link-to-home-page)

(defn jojon-panel []
  [re-com/v-box
   :gap "1em"
   :children [[jojon-box] [link-to-about-page] [link-to-home-page]]])

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
  (let [active-panel (re-frame/subscribe [:active-panel])]
    (fn []
      [re-com/v-box
       :height "100%"
       :children [(panels @active-panel)]])))
