## Тестирование 4-х способов записи объектов и их чтения.

### Размерность входного датасета = 600000.

|     serialize type     | write time | read time  | size in bytes |
|:----------------------:|:----------:|:----------:|:-------------:|
|     auto serialize     | 19.73 sec  | 33.829 sec |  38 695 705   |
| serialize with methods | 27.091 sec | 28.248 sec |  34 333 644   |
|      externalize       | 20.312 sec | 20.314 sec |  34 347 586   |
|    custom serialize    | 38.968 sec | 26.909 sec |  24 771 620   |

* Исходя из полученных результатов, можно сделать очевидный вывод о том, что ручная сериализация оказалась 
намного эффективнее по памяти. При ручной записи разработчик определяет сам, как лучше закодировать
тот или иной тип данных. Однако ручная сериализация проигрывает всем остальным по времени записи. По моему мнению, это
может быть связано с использованием `DataInputStream` и `DataOutputStream` вместо, соответственно, `ObjectInputStream` 
и `ObjectOutputStream` для чтения и записи данных.
* Автоматическая сериализация оказывается самой быстрой по времени записи, но и также самой медленной по времени чтения. 
Одной из причин такой работы является обращение к полям объектов с использованием `Reflection API`. Видно, что данная
сериализация еще и оказывается самой неэффективной по памяти из-за записи данных напрямую файл, без их предварительной
обработки (использование дефолтного механизма). 
* Пользовательская сериализация и экстернализация очень схожи по памяти выходного файла, однако скорость работы второй
оказывается намного меньше. Объяснить это можно тем, что `Serializable` во время работы генерирует большой объем служебной
информации и разного рода временных данных, а также использует `Reflection API`. Интерфейс пользовательской сериализации не
имеет методов или полей и служит только для определения семантики сериализации. `Externalizable` рационально применять, когда вы хотите
получить ограниченную сериализацию или не хотите, чтобы какая-то часть вашего объекта была сериализована. Большой недостаток
`Externalizable` заключается в том, что разработчик должен сам поддерживать логику, если происходит
добавление, удаление или изменение поля в классе.