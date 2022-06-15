(ns lal.core
     (:require [reagent.core :as r :refer [atom]]))

(enable-console-print!)

(defonce booking (atom {:status false}))

(def site-content
  {:main-header "Love and Labradorite"
   :tagline "Dedicated to helping and healing any soul in need"
   :description "A business, pioneered by Kira Naftzinger, dedicated to helping and healing any soul in need. We offer readings of Tarot and Rune readings."
   :what-we-offer {:tarot {:heading "Tarot Readings"
                           :desc "Tarot readings by Kira, choose from a number of decks, or reader's choice. $30 per 1/2 hour"}
                   :rune {:heading "Rune Readings"
                          :desc "Rune Readings"}}
   :body-content "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Placerat in egestas erat imperdiet sed. Nulla aliquet porttitor lacus luctus accumsan. Nulla facilisi nullam vehicula ipsum a arcu. Ut venenatis tellus in metus vulputate eu scelerisque felis imperdiet. Arcu non sodales neque sodales ut etiam sit amet. Nisi est sit amet facilisis magna etiam tempor orci eu. Ac turpis egestas maecenas pharetra convallis posuere morbi leo urna. Sit amet aliquam id diam maecenas ultricies mi eget mauris. Aliquam id diam maecenas ultricies. Egestas tellus rutrum tellus pellentesque eu tincidunt tortor. Sagittis eu volutpat odio facilisis mauris sit.

Arcu ac tortor dignissim convallis aenean et tortor at. Nec sagittis aliquam malesuada bibendum arcu vitae elementum curabitur. Magnis dis parturient montes nascetur ridiculus mus. Amet luctus venenatis lectus magna fringilla urna porttitor. Diam quam nulla porttitor massa id neque aliquam vestibulum. Magna fringilla urna porttitor rhoncus dolor purus non enim. Enim diam vulputate ut pharetra sit. Quam viverra orci sagittis eu volutpat odio. In hac habitasse platea dictumst vestibulum rhoncus est pellentesque. Neque gravida in fermentum et sollicitudin ac orci phasellus. Felis imperdiet proin fermentum leo vel orci porta non pulvinar. Eget nunc lobortis mattis aliquam faucibus purus in massa tempor.

Volutpat diam ut venenatis tellus in metus vulputate. Facilisi etiam dignissim diam quis enim lobortis. Rutrum tellus pellentesque eu tincidunt tortor aliquam nulla facilisi. Ullamcorper malesuada proin libero nunc consequat interdum varius sit. A diam sollicitudin tempor id eu nisl nunc. Porttitor eget dolor morbi non arcu. Mus mauris vitae ultricies leo integer malesuada nunc vel. Nunc mi ipsum faucibus vitae aliquet nec. Integer quis auctor elit sed. Cras semper auctor neque vitae tempus quam."})

(defn toggle-class
  [a k c1 c2]
  (if (= (@a k) c1)
    (swap! a assoc k c2)
    (swap! a assoc k c1)))

(defn header
  []
  (fn []
    [:div {:class "container" :margin "auto" :width "100%"}
     [:h1 {:class "main_header"} (get site-content :main-header)]]))


(defn booking-form
  [class]    
  (fn
    []
    [:div {:class (@class :booking-form)}
     [:div {:class "modal-background"}]
     [:div {:class "modal-content"}
      [:p "Full Name (First and Last): "
       [:input {:class "textbox" :name "fname"}]]
      [:br]
      [:div
       [:p "Preferred Contact Method"]
       [:p "Contact me by phone: "
        [:input {:type "radio" :name "phone-radio"}]]
       [:p "Contact me by email: "
        [:input {:type "radio" :name "email-radio"}]]]
      [:br]
      [:input {:class "textbox"}]]
     [:button {:class"modal-close is-large"
               :on-click #(do
                            (toggle-class class :booking-form "modal" "modal is-active")
                            (toggle-class class :booking-btn "button" "button has-background-success"))}]]))


(defn booking-btn
  [class]
    (fn []
            [:input {:class (@class :booking-btn)
               :value  "Book Appointment"
                     :on-click #(do
                                  (toggle-class class :booking-form "modal" "modal is-active")
                                  (toggle-class class :booking-btn "button" "button has-background-success"))}]))

(defn main-content
  []
  (fn
    []
    [:div
     [:h2 {:class "tagline"} (get site-content :tagline)]
     [:h2 {:class "description"} (get site-content :description)]]
    [:div {:class "what_we_offer"}
     (let [item (get site-content :what-we-offer)]
       [:ul (for [sub item]
                [:ul (for [desc sub]
                    [:li (get desc :header)]
                    [:li (get desc :desc)])])])]))

(defn app
  []
  (let [local-state (if (= (@booking :status) false)
                      (atom {:booking-form "modal"
                             :booking-btn "button"})
                      (atom {:booking-form "modal is-active"
                             :booking-btn "button has-background-success"}))]
    (fn []
      [:div
       {:class "conatiner" :margin "" :width "100%"}
       [header]
       [main-content]
       [booking-form local-state]
       [booking-btn local-state]])))



(r/render-component [app]
                    (.getElementById js/document "app"))
