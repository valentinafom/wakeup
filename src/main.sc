
theme: /

    state: Start
        q!: $regex</start>
        go!: /Hello_World

    state: Hello_World
        SberCard:
            actions = [{"buttons":[{ "name": "Будильник", "transition": "/alarm_intro"}, {"name" : "Расскажи про известных людей", "transition" : "/celebrity_Intro"}, {"name" : "Помощь", "transition" : "/help"}],"type":"buttons"}]
            imageUrl = https://sberdevices2.s3pd01.sbercloud.ru/smartmarket-smide-prod/84366/84367/7YmJXXjOU4KvqMkX.PNG
            button = {"name":"","transition":"/newNode_4"}
            cardTitle = Нейробудильник Вставайка!
            description = Привет! Я - Вставайка. Я помогу подобрать оптимальное время утреннего подъёма и рассказать про привычки известных людей.
        script:
            var reply = {
                "type":"text",
                "tts":"мой ответ",
            };
            $response.replies = $response.replies || [];
            $response.replies.push(reply);
    

    state: newNode_4
        a: Я задам Вам пару вопросов, а нейросеть на основе Ваших ответов посоветует оптимальное время установки будильника на следующий день. Давайте попробуем!
        go!: /ageInputScreen

    state: newNode_5
        state: 1
            e!: Будильник
            e!: Во сколько вставать
            e!: Поставь будильник
            e!: Время сна
            e!: Подъём

            go!: /alarm_intro

        state: 2
            e!: Рутина
            e!: Покажи привычки
            e!: Привычки
            e!: Привычки известных людей
            go!: /celebrity_Intro
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_5",
                name: "newNode_5 buttons",
                handler: function($context) {
                }
            });

#кандидат а удаление
    state: newNode_1
        a: Я могу подсказать наилучшее время для будильника!
        a: Даже показать утреннюю рутину известных людей!
        a: Или скажи - "Подсказка"
        go!: /newNode_3
    @IntentGroup
        {
          "boundsTo" : "/newNode_1",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : true
        }
    
    state: newNode_3
        state: 1
            e!: Будильник

            go!: /alarm_intro

        state: 2
            e!: Рутина

            go!: /celebrity_Intro

        state: 3
            e!: Подсказка

            go!: /newNode_4
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_3",
                name: "newNode_3 buttons",
                handler: function($context) {
                }
            });

    state: celebrity_Intro
        a: Показать списком (Скажите "Список", или назовёте определённое имя (Скажите "Имя")?
        go!: /celebrity_list
    @IntentGroup
        {
          "boundsTo" : "/celebrity_Intro",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    state: newNode_29
        state: 1
            e: Список

            go!: /celebrity_list

        state: 2
            e: Имя

            go!: /celeb1
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_29",
                name: "newNode_29 buttons",
                handler: function($context) {
                }
            });

    state: alarm_intro
        a: Для определения оптимальной продолжительности сна мне нужно узнать немного о Вас.
        go!: /ageInputScreen

    state: ageInputScreen
        InputNumber:
            actions = [{"buttons":[],"type":"buttons"}]
            prompt = Подскажите, сколько вам лет?
            varName = age
            failureMessage = ["Введите число ваших полных лет"]
            then = /newNode_8
            minValue = 1
            maxValue = 200

    state: newNode_8
        a: Во сколько часов вы хотите лечь спать?
        go!: /newNode_13
    @IntentGroup
        {
          "boundsTo" : "/newNode_8",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    state: newNode_13
        state: 1
            q: $DATETIME
            go!: /newNode_11

        state: Fallback
            event: noMatch
            go!: /newNode_18
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_13",
                name: "newNode_13 buttons",
                handler: function($context) {
                }
            });

    state: newNode_11
        if: $session.age < 5
            go!: /newNode_12
        elseif: $session.age>=5; $session.age<12
            go!: /newNode_14
        elseif: $session.age>=12; $session.age<18
            go!: /newNode_15
        elseif: $session.age>=18; $session.age<45
            go!: /newNode_16
        else:
            go!: /newNode_17

    state: newNode_18
        a: Пожалуйста, укажите корректное время
        # Transition /newNode_19
        go!: /newNode_8

    state: newNode_12
        script:
            $session.wtime=14-(24-$session.DATETIME.hour)
        a: Надо поставить будильник на {{$session.wtime}} часов
        # Transition /newNode_21
        go!: /newNode_20

    state: newNode_14
        script:
            $session.wtime=12-(24-$session.DATETIME.hour)
        a: Надо поставить будильник на {{$session.wtime}} часов
        # Transition /newNode_22
        go!: /newNode_20

    state: newNode_15
        script:
            $session.wtime=10-(24-$session.DATETIME.hour)
        a: Надо поставить будильник на {{$session.wtime}} часов
        # Transition /newNode_23
        go!: /newNode_20

    state: newNode_16
        script:
            $session.wtime=8-(24-$session.DATETIME.hour)
        a: Я рекомендую поставить будильник на {{$session.wtime}} часов
        # Transition /newNode_24
        go!: /newNode_20

    state: newNode_17
        script:
            $session.wtime=6-(24-$session.DATETIME.hour)
        a: Я рекомендую поставить будильник на {{$session.wtime}} часов
        # Transition /newNode_25
        go!: /newNode_20

    state: newNode_20
        a: Рассказать про утренние привычки известных людей?
        go!: /newNode_26
    @IntentGroup
        {
          "boundsTo" : "/newNode_20",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    state: newNode_26
        state: 1
            q: $AGREEMENT

            go!: /celebrity_Intro

        state: 2
            q: $NEGATION

            go!: /newNode_27
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_26",
                name: "newNode_26 buttons",
                handler: function($context) {
                }
            });

    state: newNode_27
        a: Тогда всего Вам хорошего, обращайтесь!
        go!: /newNode_28

    state: newNode_28
        EndSession:
        
    state: help
        a: Нейросеть задаст вам пару вопросов и посоветует оптимальное время установки будильника на следующий день!  Просто скажи - "Будильник"
        a: Познакомим с утренним расписанием известных людей, их рутиной ) Просто скажи - "Рутина"
        go!: /newNode_5
        actions = [{"buttons":[{ "name": "Будильник", "transition": "/alarm_intro"}, {"name" : "Расскажи про известных людей", "transition" : "/celebrity_Intro"}],"type":"buttons"}]
        script:
            var reply = {
                "type":"text",
                "text":"Нейросеть задаст вам пару вопросов и посоветует оптимальное время установки будильника на следующий день!  Просто скажи - 'Будильник'. Так же я познакомлю тебя с утренними ритуалами известных людей. Просто скажи - 'Рутина'",
                "tts":"Нейросеть задаст вам пару вопросов и посоветует оптимальное время установки будильника на следующий день. Так же я познакомлю тебя с утренними ритуалами известных людей",
            };
            $response.replies = $response.replies || [];
            $response.replies.push(reply);        
    @IntentGroup
        {
          "boundsTo" : "/newNode_4",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    
    state: celebrity_list
        CardList:
            actions = [{"buttons":[{ "name": "Будильник", "transition": "/alarm_intro"}, {"name" : "Случайная история", "transition" : "/celebrity_Intro","type":"buttons"}]
            listTitle = это список
            listSubtitle = подзаголовок
            listItems = [{
            "title":"Сальвадор Дали",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb1"}},
            {"title":"Ричард Бакминстер Фуллер",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb2"}},
            {"title":"Уинстон Черчиль",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb3"}},
            {"title":"Никола Тесла",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb4"}},
            {"title":"Марк Цукерберг",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb5"}},
            {"title":"Джек Дорси",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb6"}},
            {"title":"Илон Маск",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb7"}},
            {"title":"Барак Обама",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb8"}},
            {"title":"Опра Уинфри",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb9"}},
            {"title":"Стив Джобс",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb10"}},
            {"title":"Маргарет Тэтчер",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb11"}},
            {"title":"Бенджамин Франклин",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb12"}},
            {"title":"Арнольд Шварценеггер",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb13"}},
            {"title":"Тони Роббинс",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb14"}},
            {"title":"Ричард Брэнсон",
            "value":"значение",
            "subtitle":"",
            "iconUrl":",
            "action":{"transition": "/celeb15"}}]
            button = {"name":"кнопка","transition":"","enabled":false}
            
    state: celebrity_random
        CardList:
            actions = [{"buttons":[{ "name": "Будильник", "transition": "/alarm_intro"}, {"name" : "Случайная история", "transition" : "/celebrity_Intro","type":"buttons"}]
            listTitle = это список
            listSubtitle = подзаголовок
            listItems = [{
            "title":"заголовок 1",
            "value":"значение",
            "subtitle":"подзаголовок",
            "iconUrl":"https://sberdevices2.s3pd01.sbercloud.ru/smartmarket-smide-prod/84366/84367/fLQv202P6WiFKglU.jpg",
            "hash":"36ba9472055289ea0614b28159b65405",
            "action":{"name":"заголовок 1"}},
            {"title":"заголовок 2",
            "value":"значение2",
            "subtitle":"подзаголовок",
            "iconUrl":"",
            "hash":"",
            "action":{"name":"заголовок 2"}}]
            button = {"name":"кнопка","transition":"","enabled":false}            
            
    state: celeb1
       image: 
       a: Сальвадор Дали и Леонардо Да Винчи спали 6 раз в сутки по 20 минут каждые 4 часа.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb2
       image: 
       a: Американский архитектор, дизайнер, инженер, изобретатель, философ, математик, писатель, поэт спал 4 раза в сутки по 30 минут каждые 6 часов.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb3
       image: 
       a: Уинстон Черчиль спал 6,5 часов в сутки: 5 часов ночью и полтора часа днем. Он просыпался в 7:30, завтракал, читал газеты и работал, находясь в постели. Только к 11:00 Черчилль выходил на прогулку в сад и наливал себе виски с содовой. Не все так могут - продуктивно работать в постели!
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb4
       image: 
       a: Тесла спал всего 2 часа ночью и 20 минут днём
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb5
       image: 
       a: Мне не удалось найти сколько спит Марк Цукерберг, но с утра он не заморачивается выбором одежды - это позволяет ему принимать на одно решение в день меньше
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb6
       image: 
       a: После пробуждения создатель Твиттера занимается медитацией, а потом отправляется на пробежку в 10 километров.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb7
       image: 
       a: Миллиардер очень много работает, но всегда спит по шесть часов. Его утро начинается в 7:00, первым делом он проверяет почту и в течение получаса отвечает на самые важные и срочные письма. Бизнесмен не всегда успевает позавтракать, но никогда не пренебрегает душем.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb8
       image: 
       a: В 6:45 утра экс-президент США обязательно тренируется, совмещая силовые упражнения с кардио. После завтракает с семьёй и помогает дочкам собраться в школу.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb9
       image: 
       a: Одна из самых влиятельных женщин в мире по версии журнала Forbes начинает своё утро с 20 минут медитации, затем не менее 15 минут на беговой дорожке и — самое важное — сбалансированный завтрак, состоящий из белков, жиров и сложных углеводов.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb10
       image: 
       a: Создатель IPhone просыпался в шесть утра, подходил к зеркалу и спрашивал себя: «Если бы сегодня был последний день моей жизни, был бы я счастлив от того, что собираюсь сделать?». Работал до тех пор, пока не проснулись дети, завтракал с семьей, а затем опять возвращался к работе.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb11
       image: 
       a: Даже затянувшиеся допоздна деловые встречи не мешали Железной леди проснуться следующим утром в пять часов, чтобы послушать любимую передачу о еде и сельском хозяйстве по радио.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb12
       image: 
       a: У Бенджамина Франклина день буквально был расписан по часам. Утренняя рутина занимала у политика три часа. Он просыпался в пять утра, умывался, завтракал и планировал свой день. А ещё утром Франклин обязательно задавал себе вопрос: «Что хорошего я сделаю сегодня?» И в восемь он уже начинал работать.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb13
       image: 
       a: Вот так выгрядит утро терминатора: подъем в 5 утра, чтение новостей, проверка почты, 45-минутные упражнения, завтрак, душ и вперёд за работу!
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb14
       image: 
       a: Так выглядит утро известного инфоцыгана Тони Робинса: ранний подъем, дыхательные упражнения, десятиминутная визуализация, 15-30 минут физических упражнений
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random
    state: celeb15
       image: 
       a: Один из самых богатых жителей Великобритании с состоянием более чем 5 миллиардов долларов США просыпается в 5:45, затем идёт плавать или играть в теннис, после таких утренних упражнений завтракает.
       buttons:
           "Будильник" -> /alarm_intro
           "Помощь" -> /help
           "Ещё" -> /celebrity_random