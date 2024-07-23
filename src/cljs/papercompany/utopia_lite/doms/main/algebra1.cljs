(ns papercompany.utopia-lite.doms.main.algebra1
  (:require
   [papercompany.utopia-lite.markdown :as markdown]
   [reagent.core :as reagent]
   [clojure.string :as str]))

(set! *warn-on-infer* false)

(def article1
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 1조 [대수]</div>"
           "##### 제 1절"
           "- **대수**(algebra)란 그것의 완성된 모양에는 한계를 두지 않은 채, 완성된 모양이 꼭 지녀야할 기틀을 마련해주는 출발점이다."
           "- 유토피아의 창조자가 **대수**를 창조하면 유토피아의 국민들이 세부사항을 스스로 채워나가길 원한다."
           "- 유토피아의 창조자가 창조한 **대수**를 **백학대수**라고 부른다."
           "- 유토피아의 국민들이 만들어가는 세부사항을 **해석**(analysis)이라고 부른다."
           ""
           "  "
           "##### 제 2절"
           "- 유토피아는 현실세계와 다른 세상이며, 창조자가 존재하는 세상이다."
           "- 유토피아의 **백학대수**에서 굵은 글씨로 작성되는 단어들은 유토피아 내에서만 사용되는 특별한 의미로 새로 정의되거나 재정의되는 단어들이다."])))

(def article2
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 2조 [유토피아]</div>"
           "##### 제 1절"
           "- 유토피아는 **메타버스**로, 창조자가 존재하는 가상세계이다."
           "- **메타버스**의 정의는 다음과 같다."
           ""
           "  "
           "##### 제 2절"
           "- **메타버스**는 정부가 존재하는 SNS이다."
           "- 유토피아는 질서가 있는 SNS를 표방하며, 그 질서는 유토피아의 사용자들이 직접 선출한 정부에 의해서 이루어진다."
           "- **메타버스**의 정부는 의사결정 과정에서 사용자들의 의견을 직접적으로 수렴한다."
           ""
           "  "
           "##### 제 3절"
           "- **메타버스**는 금융의 장이다."
           "- 유토피아는 경쟁과 논쟁을 통해 의견을 수립하고 정부를 건설하며, 그 경쟁의 방식으로 금융을 선택한다."
           "- 유토피아는 다양한 **코인**들이 경쟁하며 토론하는 장이다."
           ""
           "  "
           "##### 제 4절"
           "- **메타버스**는 자기계발의 장이다."
           "- 유토피아는 **코인**의 가격상승 동력은 타인의 자기계발을 함께 응원하는 **공감**으로부터 기인한다."])))

(def article3
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 3조 [철학]</div>"
           "##### 제 1절"
           "- 유토피아는 **자유**, **민주**, **정의**, **공감**의 철학을 표방한다."
           ""
           "  "
           "##### 제 2절"
           "- **자유**는 개인의 능력 계발이 장려되며, 훌륭하게 능력을 계발한 사람이 충분한 보상을 받게 하자고 주장하는 정신이다."
           "- **자유**는 서로의 경쟁과 다툼, 그 과정에서 서로의 성장을 장려하며 이긴 사람을 더 존중해준다."
           "- **자유**는 모두가 꿈을 꾸고 각자의 보상을 얻어 행복을 추구할 수 있는 권리이다."
           ""
           "  "
           "##### 제 3절"
           "- **민주**는 새로운 사람들이 경쟁에 참여하는 것을 배척하지 않는다는 정신이다."
           "- 경쟁은 열린 광장에서 이루어져야 사회 전반적인 발전을 이루고 위험에 대비할 수 있다."
           "- **민주**는 경쟁에 있어서 공익에 해가 되는 차별을 진행하지 않는다는 철학이 포함된다."
           ""
           "  "
           "##### 제 4절"
           "- **정의**는 경쟁이 공익에 해가 되는 승리자를 선별하였을 경우 해당 승리자에 반대하려는 정신이다."
           "- **정의**는 경쟁과 싸움이 눈 앞의 손해를 야기하더라도 장기적 관점으로 공익의 대변자가 현재의 승자에게 도전할 수 있게 장려하는 정신이다."
           "- **정의**는 손해를 야기하는 싸움의 최소화를 위해 **자유**에 제한을 가하는 정신을 포함한다."
           ""
           "  "
           "##### 제 5절"
           "- **공감**은 경쟁의 끝에서 양 쪽의 사상을 융화하고 진화시켜나가는 정신이다."
           "- 유토피아의 경쟁과 논쟁의 과정에서 서로는 서로의 생각을 수용하며, 승자가 얻는 권리는 패자를 위해 사용된다."])))

(def article4
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 4조 [계승]</div>"
           "##### 제 1절"
           "- 유토피아는 군사, 화폐, 기업(주식)으로 이어지는 현대의 힘의 분배를 계승한다."
           "- 유토피아는 페이퍼컴퍼니의 자사주를 다시 개인(**코인**)으로 분배한다."
           ""
           "  "
           "##### 제 2절"
           "- **코인**이란, 개인이 자신의 내재가치를 형상화 하여 발행하는 가상재화이다."
           "- **코인**에 대한 자세한 설명은 **백학대수** 제 10조에 서술되어있다."
           ""
           "  "
           "##### 제 3절"
           "- 유토피아는 계승하는 현실세계에 대항하지 않는다."
           "- 군사, 화폐, 주식이라는 힘이 **코인**의 가치를 부정할 경우, **코인**은 그 힘을 현실세계에 반환한다."
           ""
           "  "
           "##### 제 4절"
           "- 페이퍼컴퍼니의 주식은 민간기업이나 개인에게 판매되지 않는다."
           "- 페이퍼컴퍼니의 모든 주식은 대한민국 공권력과 자사주로 이루어진다."
           "- 민간의 투자는 주식이 아닌 **코인**을 거래하여 이루어진다."
           ""
           "  "
           "##### 제 5절"
           "- 유토피아의 지도자들은 책임이 강조된다."
           "- 지도자들은 각자의 고유한 역할이 존재하며, 각 영역별로 유일하게 선출된다."])))

(def article5
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 5조 [단]</div>"
           "##### 제 1절"
           "- 유토피아에는 세가지 종류의 **단**이 있다."
           "- **홍단**, **청단**, **초단**"
           ""
           "  "
           "##### 제 2절"
           "- 모든 유토피아 국민은 태어나서 **홍단**을 부여받는다."
           ""
           "  "
           "##### 제 3절"
           "- 자신의 **백학코인**의 **시장가격**이 기준치를 달성하였을 경우, **청단**을 획득할 수 있다."
           "- **청단**의 획득을 포기할 경우, **백학코인**의 **시장가격**은 해당 기준치를 넘지 못한다."
           ""
           "  "
           "##### 제 4절"
           "- **청단**을 획득한 사람 중, **백학코인**의 **시장가격**이 더 높은 기준치를 달성한 경우, **초단**을 획득할 수 있다."
           "- **초단**을 획득한 사람들 중에서 **두루미**, **벚꽃**, **보름달**이 선출된다."])))

(def article6
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 6조 [1월]</div>"
           "##### 제 1절"
           "- **1월**은 **두루미**가 담당하는 **달**이다."
           "- 페이퍼컴퍼니의 모든 자사주에 대한 의결권은 **두루미**가 가진다."
           "- **두루미**의 권한은 현실세계의 정부와 사법기관에 의해 제한된다."
           ""
           "  "
           "##### 제 2절"
           "- 모든 유토피아의 국민 하나당 **백학코인** 한 종류가 생성되며, 해당 코인의 유일한 발행자이다."
           "- 유토피아에서의 활동 전체가 해당 국민의 **백학코인**의 **시장가격**을 결정짓는 요소가 된다."
           ""
           "  "
           "##### 제 3절"
           "- **백학문**은 유토피아 국민 하나에 대한 자기계발을 도와주는 **문예**모음이다."
           "- **백학문**은 여섯가지 속성이 있다."
           "- **빛**, **어둠**, **불**, **물**, **바람**, **땅**"
           "- 모든 유토피아 국민은 모든 속성의 **백학문**을 자유롭게 작성할 수 있다."
           ""
           "  "
           "##### 제 4절"
           "- **빛**속성 **백학문**은 자기 자신을 대표하는 **백학문**이다."
           "- 나를 표현하는 주제(캐릭터나 단어)를 소개하고 어떤 점이 자신을 형상화하는지 설명한다."
           ""
           "  "
           "##### 제 5절"
           "- **어둠**속성 **백학문**은 소중한 자신의 생각이나 철학을 담은 **백학문**이다."
           "- **어둠**속성 **백학문**은 **빛**속성과 다르게 순서가 정해져 있지 않으며 체계적이지 않고 단편적이다."
           ""
           "  "
           "##### 제 6절"
           "- **불**속성 **백학문**은 자기 자신의 정치사상을 나타내는 **백학문**이다."
           "- 특정한 정치사상에 대해 이야기하며, 어떤 점에서 해당 정치사상에 공감하는지를 기재하는 글이다."
           ""
           "  "
           "##### 제 7절"
           "- **물**속성 **백학문**은 내가 타인의 생각을 수용한 바를 기록하는 **백학문**이다."
           "- 매체를 통해 얻은 소감을 기록할 수 있다."
           ""
           "  "
           "##### 제 8절"
           "- **바람**속성 **백학문**은 자기 자신의 전문영역을 키워나가는 과정을 기록하는 **백학문**이다."
           ""
           "  "
           "##### 제 9절"
           "- **땅**속성 **백학문**은 내가 오늘 하루 무엇을 노력하였는지에 대해 기록하는 **백학문**이다."])))

(def article7
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 7조 [3월]</div>"
           "##### 제 1절"
           "- **3월**은 **벚꽃**이 담당하는 **달**이다."
           "- **벚꽃**은 **앵화코인**을 **채택**하거나 **부결**할 수 있다."
           ""
           "  "
           "##### 제 2절"
           "- **초단**을 가진 사람들은 **앵화**를 생성할 수 있다."
           "- **앵화**는 **유한앵화**와 **무한앵화** 두가지의 종류가 있다."
           ""
           "  "
           "##### 제 5절"
           "- **유한앵화**를 생성할 때, 발의자는 선택지들을 제시하고 각 선택지들마다 **앵화코인**을 생성한다."
           ""
           "  "
           "##### 제 6절"
           "- **무한앵화**를 생성할 때, 발의자는 선택지를 제시하지 않고, **앵화코인**도 생성하지 않는다."
           "- **홍단**을 가진 사람들은 선택지를 생성할 수 있다."
           "- 선택지의 발의자는 해당 선택지에 대한 **앵화코인**을 생성한 후, 선택지가 채택되어야한다는 주장을 담은 최초의 **앵화문**을 작성한다."
           ""
           "  "
           "##### 제 7절"
           "- **앵화코인**의 구매자는 구매당시 1회 한정으로 해당 선택지에 대한 **앵화문**을 작성할 수 있다."
           "- 모든 **앵화코인**은 자신의 선택지와 관련된 **앵화문**을 열람할 수 있다."
           ""
           "  "
           "##### 제 8절"
           "- **앵화코인**들이 **시장가격**을 두고 격돌하여 논의가 진행된다."
           "- 논제가 닫힐 때에 **벚꽃**은 선택지들마다 **채택**과 **부결**을 선언한다."
           ""
           "  "
           "##### 제 9절"
           "- **무한앵화**의 경우, 비슷한 선택지들 간에 **단일화**과정이 존재한다."
           "- **단일화**에 대한 자세한 내용은 **앵화해석**에서 다루어진다."
           ""
           "  "
           "##### 제 10절"
           "- **백학대수**의 세부사항인 **앵화해석**과 **만월해석**을 만들어나가는 것은 **3월**의 중요한 역할이다."
           "- **앵화해석**은 일반적인 사안들을 다룬다."
           "- **만월해석**은 유토피아 국민으로서 지켜야할 사항들과 지키지 않았을 때의 처벌에 대해 담고 있다."])))

(def article8
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 8조 [8월]</div>"
           "##### 제 1절"
           "- **8월**은 **보름달**이 담당하는 **달**이다."
           "- **보름달**은 **만월코인**을 **채택**하거나 **부결**할 수 있다."
           ""
           "  "
           "##### 제 2절"
           "- **8월**에는 **삭월**, **회월**, **만월**, **월식**이 존재한다."
           "- **청단**을 가진 국민들은 **삭월**과 **회월**을 생성할 수 있다."
           "- 모든 국민들은 **만월**과 **월식**을 생성할 수 있다"
           ""
           "  "
           "##### 제 3절"
           "- **삭월**은 **백학문**, **앵화문**, **만월문**을 대상으로 그것에 반대하며 생성된다."
           "- **삭월**의 발의자는 해당 **삭월**에 대한 **만월코인**을 생성한 후, 최초의 **만월문**을 작성한다."
           "- 해당 코인의 구매자는 구매당시 1회 한정으로 해당 **삭월**에 대한 **만월문**을 작성할 수 있다."
           "- **삭월**의 **만월코인**은 자신을 주제로 작성된 **만월문**을 열람할 수 있다."
           "- **삭월**에 대한 **만월코인**이 **보름달**에 의해 채택된 경우, 기소된 **백학문**, **앵화문**, **만월문** 옆에 해당 **만월코인**이 나란히 나열된다."
           ""
           "  "
           "##### 제 4절"
           "- **회월**은 특정 유토피아 국민이 **만월해석**에 어긋났음을 기소하는 심판이다."
           "- **만월해석**은 유토피아인들이 유토피아에서 지켜야할 규칙이다."
           "- **회월**의 발의자는 해당 **회월**에 대한 **만월코인**을 생성한 후, 대상이 어떤 **만월해석**을 어겼는지 서술하는 최초의 **만월문**을 작성한다."
           "- 해당 코인의 구매자는 구매당시 1회 한정으로 해당 **회월**에 대한 **만월문**을 작성할 수 있다."
           "- **회월**의 **만월코인**은 자신을 주제로 작성된 **만월문**을 열람할 수 있다."
           "- **회월**에 대한 **만월코인**이 **보름달**에 의해 채택된 경우, **만월해석**에 기재된 처벌범위를 **보름달**이 설정하여 형을 집행한다."
           "- 집행된 처벌은 기소 대상의 **만월기록**에 기록된다."
           ""
           "  "
           "##### 제 5절"
           "- 유토피아는 모든 유토피아 국민들에게 청렴과 거짓없음을 강요하는 **정의**의 칼날을 겨누지 않지만, **청단**이나 **초단**의 획득자에게는 요구한다."
           "- 모든 유토피아 국민은 **청단**이나 **초단**획득자를 대상으로 **만월**을 생성할 수 있다."
           "- **만월**의 발의자는 해당 **만월**에 대한 **만월코인**을 생성한 후, 최초의 **만월문**을 작성한다."
           "- 해당 코인의 구매자는 구매당시 1회 한정으로 해당 **만월**에 대한 **만월문**을 작성할 수 있다."
           "- **만월**의 **만월코인**은 자신을 주제로 작성된 **만월문**을 열람할 수 있다."
           "- **만월**에 대한 **만월코인**이 **보름달**에 의해 채택된 경우, 기소 대상의 **프로필**에는 해당 **만월코인**이 나열된다."
           "- **만월**에 대한 변론이나 반성을 담은 **백학문**을 작성할 경우, 해당 **만월코인** 옆에 나열된다."
           "- **만월**의 발의자는 페이퍼컴퍼니에 의해 익명성이 보장된다."
           "- **만월문**이 거짓되었거나 명예훼손이 이루어지는 경우, **만월해석**이 아닌 현실세계의 형법을 따른다."
           ""
           "  "
           "##### 제 6절"
           "- 모든 유토피아 국민은 자기자신에 대한 **만월기록**에 대해 이의를 제기하는 **월식**을 생성할 수 있다."
           "- **월식**의 발의자는 해당 **월식**에 대한 **만월코인**을 생성한 후, 자신에 대한 처벌이 어째서 재고되어야하는지를 주장하는 최초의 **만월문**을 작성한다."
           "- 해당 코인의 구매자는 구매당시 1회 한정으로 해당 **월식**에 대한 **만월문**을 작성할 수 있다."
           "- **월식**의 **만월코인**은 자신을 주제로 작성된 **만월문**을 열람할 수 있다."
           "- **월식**에 대한 **만월코인**이 **보름달**에 의해 채택된 경우, 집행되었던 처벌은 철회된다."])))

(def article9
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 9조 [기축통화]</div>"
           "##### 제 1절"
           "- **표**는 유토피아의 기축통화이다."
           ""
           "  "
           "##### 제 2절"
           "- 유토피아는 **표**의 모든 사용에 대해 수수료를 걷는다."
           "- 이렇게 걷은 수수료는 **국고**에 쌓인다."
           ""
           "  "
           "##### 제 3절"
           "- **국고**의 소유자는 유토피아의 국민 전체이다."
           "- 모든 유토피아의 국민들은 자신의 **시장가치**에 해당하는 만큼 **국고**에 대한 지분을 가진다."
           "- **시장가치**란, 대상의 **시장가격** 혹은 대상이 소유한 **시장가격**의 총합을 의미하는 것으로, **표**가 함의하는 내재가치를 의미한다."
           "- **두루미**는 **배당**을 실행하여 **국고**의 일부를 **시장가치**에 비례하게 분배할 수 있다."
           ""
           "  "
           "##### 제 4절"
           "- **국고**가 쌓일 수록 유토피아의 환율은 올라간다."
           "- 유토의 환율은 **(1 + (1표 당 국고지분))원**이 된다."
           ""
           "  "
           "##### 제 5절"
           "- 페이퍼컴퍼니의 모든 형태의 수익과 지출은 **국고**에서 이루어진다."
           "- 이때 지출은 **국정비**라는 이름을 가지며, 유토피아 국민들이 적정한 금액을 산정한다."])))

(def article10
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 10조 [코인]</div>"
           "##### 제 1절"
           "- **코인**은 생성될 때 **백화해석**에서 정의된 **시장가격**으로 시작한다."
           ""
           "  "
           "##### 제 2절"
           "- **국고**는 각자의 **시장가치**에 비례하여 분배되는데, 이때 유토피아에 존재하는 모든 국민, **코인**, **표**의 **시장가치**들의 총합을 **시장규모**라고 부른다."
           ""
           "  "
           "##### 제 3절"
           "- **코인**의 생성자는 곧 **코인**의 발행자이다."
           "- **코인**의 발행자는 **코인**의 현재 **시장가격**을 **표**로 지불하여 **코인**을 원하는 때에 발행할 수 있다."
           "- 유토피아의 **시장규모**에서 발행에 사용된 **표**는 제거되고, 발행된 **코인**의 **시장가치**가 **시장규모**에 편입된다."
           "- 이 때 **시장규모**에서 제거된 **표**는 **사표**라고 부르며, 각 종류의 **코인**마다 **사표**의 총량은 기록된다."
           ""
           "  "
           "##### 제 3절"
           "- **코인**의 소유자는 해당 **코인**을 임의의 시점에 폐기하여 **표**로 반환받을 수 있다."
           "- 한 **코인**당 반환받는 **표**의 량은 해당 **코인**의 **사표** 총량을 발행된 **코인**의 총량으로 나눈 값이다."
           "- 유토피아의 시장규모에서 코인의 **시장가치**가 제거되고, 반환받는 **표**가 시장규모에 편입된다."
           "- 해당 **코인**의 **사표** 총량도 해당 금액만큼 차감된다."
           ""
           "  "
           "##### 제 4절"
           "- **코인**은 당시의 **시장가격**에서 특정한 범위를 벗어난 거래가를 가질 수 없다."
           "- 허용되는 거래가의 범위는 **시장가격**이 높을수록 더 넓다."
           ""
           "  "
           "##### 제 5절"
           "- **코인**의 **시장가격**의 변화를 야기하는 거래를 **코인**에 대한 **투표**라고 부른다."
           "- **코인**이 **시장가격**으로 거래되었을 때의 거래액과 실거래액의 차이는 가격변동을 주장한다고 간주한다."
           "- 한 사람이 한 **코인**에 대해 하루동안 진행한 거래중에 가격변동을 가장 많이 야기하는 단 하나의 거래만이 실제 가격변화에 반영되며 **투표**로 간주된다."
           ""
           "  "
           "##### 제 6절"
           "- **코인**에는 수명이 존재한다."
           "- 수명이 다한 **코인**은 시장가격이 고정되며, 투자자산으로서의 의미를 잃고 수표로서의 의미를 가지게 된다."
           "- **코인**의 발행자가 사망할 경우 수명이 다한다."
           "- **코인**이 채택되거나 부결되는 경우에도 수명이 다한다."
           "- 채택되는 경우 **시장가격** 보너스가 주어진다."
           "- 부결되는 경우 **시장가격** 패널티가 주어진다."])))

(def article11
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 11조 [거래]</div>"
           "##### 제 1절"
           "- 유토피아에서 **코인**의 거래는 **직접거래**, **간접거래**, **자가거래**의 세종류가 있다."
           ""
           "  "
           "##### 제 2절"
           "- **직접거래**는 **코인**의 구매의사가 있는 사람이 구매의사를 밝히면, 판매의사를 지닌 사람이 구매자에게 직접 접촉을 하는 방식이다."
           "- **직접거래**에서 구매자와 판매자는 주 대상이 되는 **코인**과 **표** 뿐 아니라, 자신들이 보유한 다양한 **코인**들을 거래에 포함시킬 수 있다."
           "- **직접거래**에서 양 측이 제시하는 거래가가 동일하게 평형을 이루어야 하며, 각각의 **코인**들은 **시장가격**의 적절한 범위 내에서 거래되어야 한다."
           ""
           "  "
           "##### 제 3절"
           "- **간접거래**에서 **코인**의 구매자는 자신이 원하는 구매액으로 구매를 신청하고, 판매자는 자신이 원하는 판매액으로 판매를 신청한다."
           "- 거래가가 구매자의 희망 구매액보다 낮고 판매자의 희망 판매액보다 높다면 거래는 즉시 자동으로 체결된다."
           "- 더 높은 금액으로 구매를 신청한 사람이 우선적으로, 더 낮은 금액으로 판매를 신청한 사람이 우선적으로 매칭된다."
           ""
           "  "
           "##### 제 4절"
           "- **코인**의 소유자는 하루에 한번 자기 자신과 **자가거래**를 할 수 있다."
           "- **자가거래**에서 자신은 특정한 **코인**에 대한 거래가를 설정한 후, 해당 거래가의 **표**를 통해 해당 **코인**을 자기 자신에게 재구매한다."
           "- 거래의 결과는 거래 수수료의 지불 이외의 효과를 지니지 않는다."
           "- **자가거래**에서 **코인**의 거래가격은 **시장가격**보다 더 높아야 한다."
           "- 이 거래는 **투표**활동에 반영된다."
           ""
           "  "
           "##### 제 5절"
           "- **직접거래**는 **간접거래**보다 노력이 더 많이 필요하지만, **코인**의 사용에는 **표**의 사용과는 달리 수수료가 면제된다는 장점을 누릴 수 있다."
           "- **자가거래**를 통해 **코인**의 소유권을 변경하지 않고 해당 **코인**에 대한 지지 선언을 추가로 진행할 수 있다."])))

(def article12
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 12조 [포트폴리오]</div>"
           "##### 제 1절"
           "- 유토피아의 모든 국민은 소유한 **코인**의 보유 현황을 투명하게 공개한다."
           "- 하나의 국민이 소유한 코인 보유현황을 **포트폴리오**라고 한다."
           ""
           "  "
           "##### 제 2절"
           "- **코인**을 보유한다는 것은 해당 **코인**에 대한 지지선언으로 간주한다."
           "- 유토피아의 국민들은 모든 **코인**의 구매에 대해 책임을 가진다."])))

(def article13
  (apply str
         (interpose
          "\n"
          ["<div style=\"margin-top:1em;margin-bottom:1em;font-size:1.5em;color:#ba3925\">제 13조 [Lite]</div>"
           "##### 제 1절"
           "- 제 13조는 유토피아-Lite에만 한정되는 특별법이다."
           ""
           "  "
           "##### 제 2절"
           "- 유토피아-Lite의 국민들은 유토피아-Lite가 보안위협에 취약하다는 사실을 인지한다."
           ""
           "  "
           "##### 제 3절"
           "- 유토피아-Lite의 국민들은 유토피아-Lite가 대규모 트래픽에 취약하다는 사실을 인지한다."
           ""
           "  "
           "##### 제 4절"
           "- 유토피아의 창업자를 유토피아-Lite의 초대 **두루미**로 지정한다."
           "- 유토피아가 정상궤도에 오를 때 까지 초대 **두루미**가 독단적인 조치를 취할수 있음을 인지한다."
           ""
           "  "
           "##### 제 5절"
           "- 유토피아-Lite의 국민들은 유토피아-Lite의 지표들을 페이퍼컴퍼니의 개발자가 조작할 수 있음을 인지한다."
           "- 유토피아의 초대 **두루미**이자 페이퍼컴퍼니의 창업자이자 유토피아-Lite의 유일한 개발자는 공정함을 최대한 제공할 것을 엄숙히 약속한다."
           ""
           "  "
           "##### 제 6절"
           "- 유토피아는 보안위협과 대규모 트래픽을 견딜 기술력을 획득하고, 현실세계의 공권력을 통해 유토피아 내부의  부정부패를 배척할 힘이 생겼을때, 유토피아-프리덤으로 정식으로 재출발한다."
           "- 유토피아는 유토피아-Lite에 기록된 수많은 마음과 코인들을 모아 유토피아-프리덤에 소중히 이전할 것을 약속한다."])))

(def articles [article1 article2 article3 article4 article5 article6 article7 article8 article9 article10 article11 article12 article13])

(defn scroll-top-handler [states]
  (fn []
    (.scrollTo
     (.getElementById js/document "algebra1-scroll")
     #js {:left (-
                 (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                             60
                                             80))
                 (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                          0.8)
                       (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                         180
                         240))
                    2))
          :behavior "smooth"})
    (.scrollTo
     (.getElementById js/document "algebra1-content")
     #js {:top 0
          :behavior "smooth"})))

(defn resize-handler [states]
  (fn []))

(defn init [states]
  (fn [_]
    (reset! (:papercompany.utopia-lite.doms.main/name states) "백학대수")
    (reset! (:papercompany.utopia-lite.doms.main/details states) "유토피아의 기본 작동원리")
    (reset! (::content states) article1)
    (reset! (::max-page states) 13)
    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 200)
    (js/setTimeout (scroll-top-handler states) 225)))

(defn dom [states]
  (reagent/with-let [states (merge states {::touch-start-x (reagent/atom nil)
                                           ::transition-x (reagent/atom 0)
                                           ::content (reagent/atom "")
                                           ::page (reagent/atom 1)
                                           ::max-page (reagent/atom 0)})
                     _ (js/setTimeout #(do
                                         (reset! (:papercompany.utopia-lite.doms.main/resize-handler states) (resize-handler states))
                                         (reset! (:papercompany.utopia-lite.doms.main/scroll-top-handler states) (scroll-top-handler states))
                                         (reset! (:papercompany.utopia-lite.doms.main/init states) (init states))) 25)
                     _ ((init states) nil)]
    [:div {:style {:display "flex"
                   :flex-direction "column"}}
     [:div {:style {:display "flex"
                    :flex-direction "column"
                    :align-items "center"
                    :width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                    :color "rgba(64, 128, 128, 0.5)"}}
      [:div {:style {:width "80%"
                     :display "flex"
                     :justify-content "space-between"}}
       [:div {:style {:display "inline-flex"
                      :justify-content "center"
                      :align-items "center"
                      :width "5em"
                      :height "3em"
                      :padding-left "2em"
                      :padding-right "2em"
                      :cursor "pointer"}
              :on-click (fn [_]
                          (.scrollBy
                           (.getElementById js/document "algebra1-scroll")
                           #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                        -120
                                        -160)
                                :behavior "smooth"}))} "<"]
       (vec (concat [:div#algebra1-scroll.no-scroll-bar {:style (let [width (- (* @(:papercompany.utopia-lite.doms.main/content-width states) 0.8)
                                                                               (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                 120
                                                                                 160))]
                                                                  (if (> (* @(::max-page states)
                                                                            (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                              60
                                                                              80))
                                                                         width)
                                                                    {:width (str width "px")
                                                                     :white-space "nowrap"
                                                                     :overflow-x "auto"}
                                                                    {:display "flex"
                                                                     :justify-content "space-evenly"
                                                                     :width (str width "px")}))}]
                    (mapv (fn [x]
                            [:div {:style {:display "inline-flex"
                                           :justify-content "center"
                                           :align-items "center"
                                           :width "5em"
                                           :height "3em"
                                           :padding-left "2em"
                                           :padding-right "2em"
                                           :color (if (= @(::page states) x)
                                                    "#ba3925"
                                                    "")
                                           :cursor "pointer"}
                                   :on-click (fn [_]
                                               (reset! (::page states) x)
                                               (.scrollTo
                                                (.getElementById js/document "algebra1-scroll")
                                                #js {:left (-
                                                            (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                        60
                                                                                        80))
                                                            (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                     0.8)
                                                                  (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                    180
                                                                    240))
                                                               2))
                                                     :behavior "smooth"})
                                               (reset! (::content states) (articles (dec x)))
                                               (js/setTimeout
                                                #(.scrollTo
                                                  (.getElementById js/document "algebra1-content")
                                                  #js {:top 0
                                                       :behavior "smooth"})
                                                25)
                                               (reset! (::max-page states) 13)
                                               (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))} (str x)])
                          (range 1 (inc @(::max-page states))))))
       [:div {:style {:display "inline-flex"
                      :justify-content "center"
                      :align-items "center"
                      :width "5em"
                      :height "3em"
                      :padding-left "2em"
                      :padding-right "2em"
                      :cursor "pointer"}
              :on-click (fn [_]
                          (.scrollBy
                           (.getElementById js/document "algebra1-scroll")
                           #js {:left (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                        120
                                        160)
                                :behavior "smooth"}))} ">"]]]
     [:div#algebra1-content {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                     :height (str (- @(:papercompany.utopia-lite.doms.main/content-height states)
                                                     (* (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                          12
                                                          16) 3)) "px")
                                     :position "relative"
                                     :overflow-y "auto"}
                             :on-touch-start #(do
                                                (reset! (::transition-x states) 0)
                                                (reset! (::touch-start-x states) (.-screenX (aget (.-changedTouches %) 0))))
                             :on-touch-move #(reset! (::transition-x states)
                                                     (-
                                                      (.-screenX (aget (.-changedTouches %) 0))
                                                      @(::touch-start-x states)))
                             :on-touch-end #(let [touch-end-x (.-screenX (aget (.-changedTouches %) 0))]
                                              (cond
                                                (> touch-end-x (+
                                                                @(::touch-start-x states)
                                                                100))
                                                (do (swap! (::page states)
                                                           (fn [index] (inc (mod (- index 2) @(::max-page states)))))
                                                    (.scrollTo
                                                     (.getElementById js/document "algebra1-scroll")
                                                     #js {:left (-
                                                                 (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                             60
                                                                                             80))
                                                                 (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                          0.8)
                                                                       (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                         180
                                                                         240))
                                                                    2))
                                                          :behavior "smooth"})
                                                    (reset! (::content states) (articles (dec @(::page states))))
                                                    (js/setTimeout
                                                     (fn []
                                                       (.scrollTo
                                                        (.getElementById js/document "algebra1-content")
                                                        #js {:top 0
                                                             :behavior "smooth"}))
                                                     25)
                                                    (reset! (::max-page states) 13)
                                                    (reset! (::touch-start-x states) nil)
                                                    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25))
                                                (< touch-end-x (-
                                                                @(::touch-start-x states)
                                                                100))
                                                (do (swap! (::page states)
                                                           (fn [index] (inc (mod index @(::max-page states)))))
                                                    (.scrollTo
                                                     (.getElementById js/document "algebra1-scroll")
                                                     #js {:left (-
                                                                 (* (dec @(::page states)) (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                                             60
                                                                                             80))
                                                                 (/ (- (* @(:papercompany.utopia-lite.doms.main/content-width states)
                                                                          0.8)
                                                                       (if @(:papercompany.utopia-lite.doms.main/mobile? states)
                                                                         180
                                                                         240))
                                                                    2))
                                                          :behavior "smooth"})
                                                    (reset! (::content states) (articles (dec @(::page states))))
                                                    (js/setTimeout
                                                     (fn []
                                                       (.scrollTo
                                                        (.getElementById js/document "algebra1-content")
                                                        #js {:top 0
                                                             :behavior "smooth"}))
                                                     25)
                                                    (reset! (::max-page states) 13)
                                                    (reset! (::touch-start-x states) nil)
                                                    (js/setTimeout (:papercompany.utopia-lite.doms.main/resize-content states) 25)))
                                              (reset! (::transition-x states) 0))}
      [:div.article.user {:style {:width (str @(:papercompany.utopia-lite.doms.main/content-width states) "px")
                                  :position "relative"
                                  :top "0px"
                                  :left (str (cond
                                               (> @(::transition-x states) 20)
                                               (- 50 (/ 1000 @(::transition-x states)))
                                               (< @(::transition-x states) -20)
                                               (- -50 (/ 1000 @(::transition-x states)))
                                               (= @(::transition-x states) 0)
                                               0) "px")
                                  :padding-left "1em"
                                  :padding-right "1em"}
                          :dangerouslySetInnerHTML (markdown/innerHTML @(::content states))}]]]
    (finally
      (reset! (:papercompany.utopia-lite.doms.main/resize-handler states) (fn []))
      (reset! (:papercompany.utopia-lite.doms.main/scroll-top-handler states) (fn [])))))
