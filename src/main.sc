theme: /

    state: newNode_2
        SberCard:
            actions = [{"buttons":[],"type":"buttons"}]
            imageUrl = https://sberdevices2.s3pd01.sbercloud.ru/smartmarket-smide-prod/84366/84367/7YmJXXjOU4KvqMkX.PNG
            button = {"name":"Во сколько вставать?","transition":"/newNode_4"}
            cardTitle = Нейробудильник Вставайка!
            description = Привет! Я - Вставайка. Я помогу подобрать оптимальное время сна и рассказать про утренние привычки известных людей.
    @IntentGroup
        {
          "boundsTo" : "/newNode_0",
          "actions" : [ {
            "buttons" : [ {
              "name" : "Узнать стоимость",
              "transition" : "/newNode_2"
            }, {
              "name" : "Узнать наличие",
              "transition" : "/newNode_2"
            }, {
              "name" : "Узнать статус заказа",
              "transition" : "/newNode_20"
            }, {
              "name" : "Сделать заказ",
              "transition" : "/newNode_2"
            } ],
            "type" : "buttons"
          } ],
          "global" : true
        }     

    state: newNode_4
        a: Нейросеть задаст вам пару вопросов и посоветует оптимальное время установки будильника на следующий день!  Просто скажи - "Будильник"
        a: Познакомим с утренним расписанием известных людей, их рутиной ) Просто скажи - "Рутина"
        go!: /newNode_5
    @IntentGroup
        {
          "boundsTo" : "/newNode_4",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    state: newNode_5
        state: 1
            e: Будильник

            go!: /newNode_6

        state: 2
            e: Рутина

            go!: /newNode_9
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_5",
                name: "newNode_5 buttons",
                handler: function($context) {
                }
            });

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

            go!: /newNode_6

        state: 2
            e!: Рутина

            go!: /newNode_9

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

    state: newNode_9
        a: Показать списком (Скажите "Список", или назовёте определённое имя (Скажите "Имя")?
        go!: /newNode_29
    @IntentGroup
        {
          "boundsTo" : "/newNode_9",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : false
        }
    state: newNode_29
        state: 1
            e: Список

            go!: /

        state: 2
            e: Имя

            go!: /
        init:
            $jsapi.bind({
                type: "postProcess",
                path: "/newNode_29",
                name: "newNode_29 buttons",
                handler: function($context) {
                }
            });

    state: newNode_6
        a: Сколько вам лет?
        go!: /newNode_7

    state: newNode_7
        InputNumber:
            actions = [{"buttons":[],"type":"buttons"}]
            prompt = Введите число
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
        a: Надо поставить будильник на {{$session.wtime}} часов
        # Transition /newNode_24
        go!: /newNode_20

    state: newNode_17
        script:
            $session.wtime=6-(24-$session.DATETIME.hour)
        a: Надо поставить будильник на {{$session.wtime}} часов
        # Transition /newNode_25
        go!: /newNode_20

    state: newNode_20
        a: Хотите узнать про утренние привычки известных людей?
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

            go!: /newNode_9

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
        
    state: newNode_0
        a: Нейросеть задаст вам пару вопросов и посоветует оптимальное время установки будильника на следующий день!  Просто скажи - "Будильник"
        a: Познакомим с утренним расписанием известных людей, их рутиной ) Просто скажи - "Рутина"
        go!: /newNode_5
    @IntentGroup
        {
          "boundsTo" : "/newNode_4",
          "actions" : [ {
            "buttons" : [ ],
            "type" : "buttons"
          } ],
          "global" : false
        }        