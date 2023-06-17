## Introducción

Este Trabajo de Fin de Grado presenta el diseño, desarrollo e implementación de la aplicación NoMoreMeetings, que
proporciona un análisis detallado del consumo de tiempo de reunión en base a los datos existentes extraídos de los
calendarios de Google de los usuarios. En el entorno laboral actual, la gestión eficaz del tiempo es esencial, y ser
capaz de comprender cómo empleamos nuestro tiempo puede ser una herramienta invaluable para mejorar la productividad y
el equilibrio entre la vida laboral y personal.

### Motivación

La proliferación de las reuniones virtuales, acentuada por el cambio hacia el trabajo remoto debido a la pandemia de
COVID-19, ha llevado a muchos trabajadores a experimentar lo que se conoce como "fatiga de las reuniones". Esta fatiga a
menudo se produce cuando las reuniones ocupan una proporción demasiado grande de la jornada laboral, dejando poco tiempo
para el trabajo concentrado y productivo. Sin embargo, a menudo es difícil para las personas hacer un seguimiento
preciso de cuánto tiempo realmente pasan en reuniones.

Además, las soluciones de calendario actuales, como Google Calendar y Microsoft Outlook, no ofrecen una funcionalidad
suficientemente personalizada para analizar el tiempo pasado en reuniones. En este contexto, la motivación de este
proyecto
es proporcionar una herramienta fácil de usar que permita a los usuarios obtener una visión detallada de cómo utilizan
su tiempo en reuniones adaptandose a sus circustancias, y en última instancia, proporcionarles las estadisticas
necesarias para tomar las decisiones adecuadas sobre el uso de su tiempo de trabajo.

### Objetivos

El objetivo principal de este proyecto es desarrollar una aplicación que permita a los usuarios de Google Calendar
analizar y visualizar de forma detallada cómo se utiliza su tiempo en reuniones. Los objetivos específicos son:

1. Proporcionar una visión detallada del tiempo gastado en reuniones aceptadas, marcadas como tentativas y periodos de "
   fuera de la oficina".
2. Permitir a los usuarios especificar su horario de trabajo y excluir ciertos periodos de tiempo (por ejemplo, la hora
   del almuerzo) del análisis.
3. Ofrecer análisis para un periodo de tiempo especificado en días por el usuario.
4. Presentar los datos de forma clara y visual a través de gráficos y textos descriptivos.

### Alcance

Este proyecto se centrará en el diseño e implementación de una aplicación web que interactúa con Google Calendar. La
aplicación será capaz de analizar y presentar datos sobre el tiempo de reunión de los usuarios. Se proporcionará un
análisis detallado del tiempo gastado en diferentes tipos de eventos de calendario y se ofrecerá la opción de excluir
ciertos periodos de tiempo del análisis.

El proyecto no incluirá la integración con otros sistemas de calendario aparte de Google Calendar. También se limitará a
proporcionar análisis basados en datos históricos y no incluirá la predicción del uso futuro del tiempo. Finalmente,
aunque la privacidad y portabilidad de los datos es una consideración importante, este proyecto no abordará las
cuestiones de
anonimización, agregación de datos o exportación de los mismos para su uso posterior.

### Alternativas al Proyecto

Existen varias herramientas en el mercado que ofrecen algunas funcionalidades similares a las de este proyecto, pero
ninguna de ellas cumple con todos los requisitos y objetivos específicos, principalmente la personalización.

1. **Google Calendar y Microsoft Outlook**: Ambas herramientas ofrecen cierta funcionalidad de análisis de calendario.
   Sin embargo, sus capacidades son bastante limitadas. No permiten un análisis detallado del tiempo de reunión, como
   distinguir entre reuniones aceptadas, marcadas como tentativas y periodos de "fuera de la oficina". Además, no
   ofrecen la posibilidad de excluir ciertos periodos de tiempo del análisis, como la hora del almuerzo.

2. **Herramientas de análisis de tiempo y productividad**: Existen varias herramientas en el mercado, como RescueTime,
   Toggl Track y Timely, que ofrecen análisis detallados del uso del tiempo. Sin embargo, estas herramientas se centran
   principalmente en el seguimiento del tiempo dedicado a las tareas y aplicaciones informáticas, en lugar de analizar
   el tiempo dedicado a las reuniones basándose en los datos del calendario.

3. **Herramientas de planificación de reuniones**: Clockwise, Clara y X.AI son algunas de las herramientas que ofrecen
   análisis de reuniones y/o funciones de programación inteligente. Aunque estas herramientas proporcionan algunos
   análisis útiles, como la identificación de conflictos de programación y sugerencias de horarios óptimos, ninguna de
   ellas ofrece el nivel de análisis detallado de las reuniones que este proyecto busca proporcionar.

Por lo tanto, aunque existen varias alternativas al proyecto, ninguna de ellas cumple con todos los requisitos y
objetivos identificados para este proyecto. De hecho, son dichas limitaciones y carencias de estas alternativas
existentes la justificación necesaria para la implementación de este proyecto.

#### Comparación con alternativas

| Funcionalidades                                 | NoMoreMeetings | Google Calendar | Microsoft Outlook | RescueTime | Toggl Track | Timely | Clockwise | Clara | X.AI |
|-------------------------------------------------|:--------------:|:---------------:|:-----------------:|:----------:|:-----------:|:------:|:---------:|:-----:|:----:|
| Análisis de tiempo en reuniones                 |       ✔        |        ✔        |         ✔         |     ✘      |      ✘      |   ✘    |     ✔     |   ✔   |  ✔   |
| Distinguir entre tipos de reuniones             |       ✔        |        ✘        |         ✘         |     ✘      |      ✘      |   ✘    |     ✘     |   ✘   |  ✘   |
| Excluir ciertos periodos de tiempo del análisis |       ✔        |        ✘        |         ✘         |     ✔      |      ✔      |   ✔    |     ✘     |   ✘   |  ✘   |
| Análisis basado en los datos del calendario     |       ✔        |        ✔        |         ✔         |     ✘      |      ✘      |   ✘    |     ✔     |   ✔   |  ✔   |
| Seguimiento del tiempo dedicado a las tareas    |       ✘        |        ✘        |         ✘         |     ✔      |      ✔      |   ✔    |     ✘     |   ✘   |  ✘   |
| Análisis detallado de las reuniones             |       ✔        |        ✘        |         ✘         |     ✘      |      ✘      |   ✘    |     ✔     |   ✔   |  ✔   |
| Identificación de conflictos de programación    |       ✘        |        ✔        |         ✔         |     ✘      |      ✘      |   ✘    |     ✔     |   ✔   |  ✔   |
| Sugerencias de horarios óptimos                 |       ✘        |        ✘        |         ✘         |     ✘      |      ✘      |   ✘    |     ✔     |   ✔   |  ✔   |
