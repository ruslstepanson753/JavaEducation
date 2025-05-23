## 	Взаимосвязи классов
	Обобщение (Generalization) показывает, что один из двух связанных классов (подтип) является частной формой другого (супертипа), который называется обобщением первого. На практике это означает, что любой экземпляр подтипа является также экземпляром супертипа. Обобщение также известно как наследование, «is a» взаимосвязь или отношение «является».
	 «Табурет» является подтипом «Мебели».
	Реализация (Implementation) — отношение между двумя элементами модели, в котором один элемент (клиент) реализует поведение, заданное другим (поставщиком). Реализация — отношение целое-часть. Поставщик, как правило является абстрактным классом или классом-интерфейсом.
	 «Кровать» реализует поведение «Мебели для сна»
## 	Какие существуют типы диаграмм?

## 	#Общие взаимосвязи
	Зависимость — это слабая форма отношения использования, при котором изменение в спецификации одного влечёт за собой изменение другого, причём обратное не обязательно. Возникает, когда объект выступает, например, в форме параметра или локальной переменной. Существует несколько именованных вариантов. Зависимость может быть между экземплярами, классами или экземпляром и классом.
	Уточнение отношений имеет отношение к уровню детализации. Один пакет уточняет другой, если в нём содержатся те же самые элементы, но в более подробном представлении. 
	Мощность/кратность/мультипликатор отношения означает число связей между каждым экземпляром класса (объектом) в начале линии с экземпляром класса в её конце. Различают следующие типичные случаи:
	| нотация | объяснение | пример |
	|:----------:|:--------------------------:|:---------------------------------------------:|
	| 0..1 | Ноль или один экземпляр | кошка имеет или не имеет хозяина |
	| 1 | Обязательно один экземпляр | у кошки одна мать |
	| 0.. или  | Ноль или более экземпляров | у кошки могут быть, а может и не быть котят |
	|  1.. | Один или более экземпляров | у кошки есть хотя бы одно место, где она спит |
	[к оглавлению](#uml)
	# Источники
	- [Википедия](https://ru.wikipedia.org/wiki/UML)
	- [Информикус](http://www.informicus.ru/)
	[Вопросы для собеседования](README.md)
## 	Что такое «диаграмма», «нотация» и «метамодель» в UML?
	Диаграмма - графическое представление совокупности элементов модели в форме связного графа, вершинам и ребрам (дугам) которого приписывается определенная семантика
	Нотация – совокупность символов и правила их применения, используются для представления понятий и связей между ними. 
	Нотация диаграммы определяет способ представления, ассоциации, множественности. Причем эти понятия должны быть точно определены.
	Метамодель – диаграмма, определяющая нотацию. 
	Метамодель помогает понять, что такое хорошо организованная, т.е. синтаксически правильная, модель.
## 	Что такое UML?
	UML – это унифицированный графический язык моделирования для описания, визуализации, проектирования и документирования объектно-ориентированных систем. UML призван поддерживать процесс моделирования на основе объектно-ориентированного подхода, организовывать взаимосвязь концептуальных и программных понятий, отражать проблемы масштабирования сложных систем.
	Отличительной особенностью UML является то, что словарь этого языка образуют графические элементы. Каждому графическому символу соответствует конкретная семантика, поэтому модель, созданная одним человеком, может однозначно быть понята другим человеком или программным средством, интерпретирующим UML. Отсюда, в частности, следует, что модель системы, представленная на UML, может автоматически быть переведена на объектно-ориентированный язык программирования, то есть, при наличии хорошего инструментального средства визуального моделирования, поддерживающего UML, построив модель, мы получим и заготовку программного кода, соответствующего этой модели.
## 	Диаграммы поведения:
	деятельности (Activity diagram) показывает разложение некоторой деятельности на её составные части. Под деятельностью понимается спецификация исполняемого поведения в виде координированного последовательного и параллельного выполнения подчинённых элементов — вложенных видов деятельности и отдельных действий, соединённых между собой потоками, которые идут от выходов одного узла к входам другого. Диаграммы деятельности используются при моделировании бизнес-процессов, технологических процессов, последовательных и параллельных вычислений.
	состояний/автомата/конечного автомата (State Machine diagram) представляет конечный автомат с простыми состояниями, переходами и композитными состояниями. Конечный автомат (State machine) — спецификация последовательности состояний, через которые проходит объект или взаимодействие в ответ на события своей жизни, а также ответные действия объекта на эти события. Конечный автомат прикреплён к исходному элементу (классу, кооперации или методу) и служит для определения поведения его экземпляров.
	вариантов использования/прецедентов (Use case diagram) отражает отношения существующие между актёрами и вариантами использования. Основная задача — представлять собой единое средство, дающее возможность заказчику, конечному пользователю и разработчику совместно обсуждать функциональность и поведение системы.
	взаимодействия (Interaction diagram):
	- коммуникации (Communication diagram) изображает взаимодействия между частями композитной структуры или ролями кооперации при этом явно указываются отношения между элементами (объектами), а время как отдельное измерение не используется (применяются порядковые номера вызовов).
	- последовательности (Sequence diagram) показывает взаимодействия объектов, упорядоченные по времени их проявления.
	- обзора взаимодействия (Interaction overview diagram) — разновидность диаграммы деятельности, включающая фрагменты диаграммы последовательности и конструкции потока управления.
	- синхронизации (Timing diagram) — альтернативное представление диаграммы последовательности, явным образом показывающее изменения состояния на линии жизни с заданной шкалой времени. Может быть полезна в приложениях реального времени.
## 	Взаимосвязи объектов классов
	Зависимость (Dependency) обозначает такое отношение между классами, что изменение спецификации класса-поставщика может повлиять на работу зависимого класса, но не наоборот.
	 «Расписание занятий» имеет зависимость от «Списка предметов». При изменении списка предметов расписание занятий будет вынуждено изменится. Однако изменение расписания занятий никак не влияет на список предметов. 
	Ассоциация (Association) показывает, что объекты одной сущности (класса) связаны с объектами другой сущности таким образом, что можно перемещаться от объектов одного класса к другому. Является общим случаем композиции и агрегации. 
	 «Студент» и «Университет» имеют ассоциацию т.к. студент может учиться в университете и этой ассоциации можно присвоить имя «учится в».
	Агрегация (Aggregation) — это разновидность ассоциации в отношении между целым и его частями. Как тип ассоциации агрегация может быть именованной. Одно отношение агрегации не может включать более двух классов (контейнер и содержимое). Агрегация встречается, когда один класс является коллекцией или контейнером других. Причём по умолчанию, агрегацией называют агрегацию по ссылке, то есть, когда время существования содержащихся классов не зависит от времени существования содержащего их класса. Если контейнер будет уничтожен, то его содержимое — нет.
	 «Студент» не является неотъемлемой частью «Группы», но в то же время, группа состоит из студентов, поэтому следует использовать агрегацию.
	Композиция (Composition) — более строгий вариант агрегации. Известна также как агрегация по значению. Композиция имеет жёсткую зависимость времени существования экземпляров класса контейнера и экземпляров содержащихся классов. Если контейнер будет уничтожен, то всё его содержимое будет также уничтожено. 
	 «Факультет» является частью «Университета» и факультет без университета существовать не может, следовательно здесь подходит композиция.
## 	Структурные диаграммы:
	классов (Class diagram) описывает структуру системы, демонстрирующая классы системы, их атрибуты, методы и зависимости между классами.
	объектов (Object diagram) демонстрирует полный или частичный снимок моделируемой системы в заданный момент времени. На диаграмме объектов отображаются экземпляры классов (объекты) системы с указанием текущих значений их атрибутов и связей между объектами.
	компонентов (Component diagram) показывает разбиение программной системы на структурные компоненты и связи (зависимости) между компонентами.
	- развёртывания/размещения (Deployment diagram) служит для моделирования работающих узлов и артефактов, развёрнутых на них.
	- пакетов (Package diagram) используется для организации элементов в группы по какому-либо признаку с целью упрощения структуры и организации работы с моделью системы.
	- профилей (Profile diagram) действует на уровне метамодели и показывает стереотип класса или пакета.
	- композитной/составной структуры (Composite structure diagram) демонстрирует внутреннюю структуру класса и, по возможности, взаимодействие элементов (частей) его внутренней структуры.
	    - кооперации (Collaboration diagram) показывает роли и взаимодействие классов в рамках кооперации.
## 	Какие виды отношений существуют в структурной диаграмме классов?

