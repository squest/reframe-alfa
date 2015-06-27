(defproject
  realfa "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0-RC2"]
                 [org.clojure/clojurescript "0.0-3308"]
                 [ring/ring-defaults "0.1.5"]
                 [http-kit "2.1.18"]
                 [reagent "0.5.0"]
                 [re-frame "0.4.1"]
                 [re-com "0.5.4"]
                 [secretary "1.2.3"]
                 [lib-noir "0.9.9"]
                 [selmer "0.8.2"]
                 [cljs-ajax "0.3.13"]]

  :source-paths ["src/clj"]

  :resource-paths ["resources"]

  :plugins [[lein-cljsbuild "1.0.6"]
            [lein-figwheel "0.3.5" :exclusions [cider/cider-nrepl]]]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds [{:id           "dev"
                        :source-paths ["src/cljs"]

                        :figwheel     {:on-jsload "realfa.core/mount-root"}

                        :compiler     {:main                 realfa.core
                                       :output-to            "resources/public/js/compiled/app.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :asset-path           "js/compiled/out"
                                       :source-map-timestamp true}}

                       {:id           "min"
                        :source-paths ["src/cljs"]
                        :compiler     {:main          realfa.core
                                       :output-to     "resources/public/js/compiled/app.js"
                                       :optimizations :advanced
                                       :pretty-print  false}}]})
