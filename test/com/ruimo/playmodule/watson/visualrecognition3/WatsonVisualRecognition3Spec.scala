package com.ruimo.playmodule.watson.visualrecognition3

import com.ruimo.playmodule.watson.visualrecognition3.ClassifyResponse
import org.specs2.mutable.Specification
import com.ruimo.scoins.Scoping._

class WatsonVisualRecognition3Spec extends Specification {
  "WatsonVisualRecognition3" should {
    "Can parse response json" in {
      val json = """
{
  "images": [
    {
      "classifiers": [
        {
          "classes": [
            {
              "class": "apple",
              "score": 0.645656
            }, {
              "class": "fruit",
              "score": 0.598688
            }, {
              "class": "food",
              "score": 0.598688
            }, {
              "class": "orange",
              "score": 0.5
            }, {
              "class": "vegetable",
              "score": 0.28905
            }, {
              "class": "tree",
              "score": 0.28905
            }
          ],
          "classifier_id": "default",
          "name": "defaultName"
        }, {
          "classes": [
            {
              "class": "orange",
              "score": 0.635488
            }
          ],
          "classifier_id": "fruits_1050835757",
          "name": "fruits"
        }
      ],
      "image": "orange-apple-banana-isolated.jpg"
    }, {
      "classifiers": [
        {
          "classes": [
            {
              "class": "fruit",
              "score": 0.916827
            }, {
              "class": "vegetation",
              "score": 0.768525
            }, {
              "class": "market",
              "score": 0.768525
            }, {
              "class": "food",
              "score": 0.377541
            }, {
              "class": "mercado",
              "score": 0.28905
            }, {
              "class": "vegetable",
              "score": 0.268941
            }
          ],
          "classifier_id": "default2",
          "name": "default2Name"
        }, {
          "classes": [
            {
              "class": "apple",
              "score": 0.541237
            }
          ],
          "classifier_id": "fruits_1050835757",
          "name": "fruits"
        }
      ],
      "resolved_url": "https://c1.staticflickr.com/9/8803/17306765722_a2d0be2f9e_b.jpg",
      "source_url": "https://flic.kr/p/snkGus"
    }
  ],
  "images_processed": 2
}
      """

      doWith(ClassifyResponse.fromString(json)) { response =>
        response.processedImageCount === 2
        doWith(response.images) { images =>
          images.size === 2
          doWith(images(0)) { image =>
            doWith(image.classifiers) { classfiers =>
              classfiers.size === 2
              doWith(classfiers(0)) { classifier =>
                doWith(classifier.classes) { classes =>
                  classes.length === 6
                  doWith(classes(0)) { cls =>
                    cls.className === "apple"
                    cls.score === 0.645656
                  }
                  doWith(classes(1)) { cls =>
                    cls.className === "fruit"
                    cls.score === 0.598688
                  }
                  doWith(classes(2)) { cls =>
                    cls.className === "food"
                    cls.score === 0.598688
                  }
                  doWith(classes(3)) { cls =>
                    cls.className === "orange"
                    cls.score === 0.5
                  }
                  doWith(classes(4)) { cls =>
                    cls.className === "vegetable"
                    cls.score === 0.28905
                  }
                  doWith(classes(5)) { cls =>
                    cls.className === "tree"
                    cls.score === 0.28905
                  }
                }
                classifier.classifierId === "default"
                classifier.name === "defaultName"
              }
              doWith(classfiers(1)) { classifier =>
                doWith(classifier.classes) { classes =>
                  classes.length === 1
                  doWith(classes(0)) { cls =>
                    cls.className === "orange"
                    cls.score === 0.635488
                  }
                }
              }
            }
            image.fileName === Some("orange-apple-banana-isolated.jpg")
            image.resolvedUrl === None
            image.sourceUrl === None
          }
          doWith(images(1)) { image =>
            doWith(image.classifiers) { classfiers =>
              classfiers.size === 2
              doWith(classfiers(0)) { classifier =>
                doWith(classifier.classes) { classes =>
                  classes.length === 6
                  doWith(classes(0)) { cls =>
                    cls.className === "fruit"
                    cls.score === 0.916827
                  }
                  doWith(classes(1)) { cls =>
                    cls.className === "vegetation"
                    cls.score === 0.768525
                  }
                  doWith(classes(2)) { cls =>
                    cls.className === "market"
                    cls.score === 0.768525
                  }
                  doWith(classes(3)) { cls =>
                    cls.className === "food"
                    cls.score === 0.377541
                  }
                  doWith(classes(4)) { cls =>
                    cls.className === "mercado"
                    cls.score === 0.28905
                  }
                  doWith(classes(5)) { cls =>
                    cls.className === "vegetable"
                    cls.score === 0.268941
                  }
                }
                classifier.classifierId === "default2"
                classifier.name === "default2Name"
              }
              doWith(classfiers(1)) { classifier =>
                doWith(classifier.classes) { classes =>
                  classes.length === 1
                  doWith(classes(0)) { cls =>
                    cls.className === "apple"
                    cls.score === 0.541237
                  }
                }
                classifier.classifierId === "fruits_1050835757"
                classifier.name === "fruits"
              }
            }
            image.fileName === None
            image.resolvedUrl === Some("https://c1.staticflickr.com/9/8803/17306765722_a2d0be2f9e_b.jpg")
            image.sourceUrl === Some("https://flic.kr/p/snkGus")
          }
        }
      }
    }
  }
}
