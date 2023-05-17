# Descripción funcional

NoMoreMeetings es una aplicación web diseñada para optimizar la comprensión del tiempo dedicado a las reuniones laborales. Para facilitar el acceso, permite el registro y el inicio de sesión a través de la cuenta de Google del usuario.

Una vez que un usuario accede, la aplicación proporciona una página de configuración de perfil donde se pueden personalizar las horas de trabajo. Este ajuste es crucial para la aplicación, ya que los cálculos que realiza se basan en estas horas.

Además, NoMoreMeetings permite a los usuarios especificar qué calendario se debe tener en cuenta para los cálculos, proporcionando la capacidad de seleccionar aquel calendario que sea relevante para el trabajo. Los usuarios también pueden seleccionar el período temporal que desean utilizar para los cálculos, permitiendo realizar los cálculos solo en las horas de trabajo.

La aplicación se encarga de mostrar los datos de una manera fácil de entender. Ofrece una página de visualización de datos donde los usuarios pueden ver cuánto tiempo han gastado en reuniones. No solo proporciona el tiempo total, sino que también muestra una gráfica para ayudar a visualizar la distribución del tiempo. Así, se puede apreciar claramente el porcentaje de tiempo dedicado a reuniones frente al tiempo libre.

La aplicación ofrece la opción de configurar el tiempo entre reuniones que se cuenta como tiempo de reunión. Esta selección se refleja en los cálculos que realiza la aplicación.

# Req. Funcionales
1.  La aplicación debe permitir a los usuarios registrarse utilizando su cuenta de Google.
2.  La aplicación debe permitir a los usuarios registrados iniciar sesión utilizando su cuenta de Google.
3.  La aplicación debe proporcionar una página de configuración de perfil para usuarios con sesión iniciada. 
4.  La aplicación debe permitir a los usuarios con sesión iniciada editar sus horas de trabajo en la página de configuración de perfil.
5.  La aplicación debe reflejar los cambios en las horas de trabajo de un usuario en sus cálculos.
6.  La aplicación debe permitir a los usuarios con sesión iniciada seleccionar qué calendarios utilizar para los cálculos en la página de configuración de perfil.
7.  La aplicación debe reflejar los cambios en los calendarios seleccionados por un usuario en sus cálculos.
8.  La aplicación debe permitir a los usuarios con sesión iniciada seleccionar el período temporal a utilizar para los cálculos en la página de configuración de perfil.
9.  La aplicación debe reflejar los cambios en el período temporal seleccionado por un usuario en sus cálculos.
10.  La aplicación debe proporcionar una página de visualización de datos para usuarios con sesión iniciada.
11.  La aplicación debe mostrar a los usuarios con sesión iniciada la cantidad de tiempo que han gastado en reuniones en la página de visualización de datos.
12.  La aplicación debe mostrar a los usuarios con sesión iniciada una gráfica que refleje el tiempo que han gastado en reuniones en la página de visualización de datos.
13.  La aplicación debe mostrar a los usuarios con sesión iniciada el porcentaje de tiempo que han gastado en reuniones y el porcentaje de tiempo libre en la página de visualización de datos.
14.  La aplicación debe permitir a los usuarios con sesión iniciada configurar el tiempo entre reuniones que cuenta como tiempo de reunión en la página de configuración de perfil.
15.  La aplicación debe tener en cuenta el tiempo seleccionado por un usuario entre reuniones como tiempo de reunión al realizar los cálculos.

# Req. No funcionales

1. **Rendimiento**: La aplicación deberá procesar las solicitudes de los usuarios en un tiempo promedio inferior a 5 segundos.

2. **Disponibilidad**: La aplicación deberá tener una disponibilidad del 99%, lo que implica que puede permitirse hasta 87.6 horas de inactividad al año.

3. **Escalabilidad**: La aplicación deberá ser capaz de manejar un incremento del 50% en el número de usuarios concurrentes en 1 minuto sin degradación del rendimiento por encima del 10%.

4. **Accesibilidad**: La aplicación deberá cumplir con las pautas de accesibilidad WCAG 2.1 Nivel A, permitiendo su uso a usuarios con discapacidades.

5. **Seguridad**: La aplicación deberá cumplir con los estándares de seguridad y privacidad, incluyendo el Reglamento General de Protección de Datos (GDPR). Todos los datos deberán ser encriptados en tránsito y en reposo.

6. **Compatibilidad de navegador**: La aplicación deberá ser compatible y funcional en las últimas versiones de los navegadores más utilizados (Google Chrome, Mozilla Firefox, Safari y Microsoft Edge).

7. **Interoperabilidad**: La aplicación deberá interactuar de manera eficiente con las APIs de Google, en particular con Google Calendar y Google Authentication.

8. **Usabilidad**: La aplicación debe ser intuitiva y fácil de usar, con una interfaz de usuario que requiera un mínimo de capacitación para los nuevos usuarios.

9. **Mantenibilidad**: El código de la aplicación deberá seguir las buenas prácticas de programación, con una cobertura de pruebas unitarias superior al 70%.

10. **Resiliencia**: La aplicación deberá ser capaz de recuperarse de fallos y reanudar su funcionamiento normal en un tiempo inferior a 1 hora.

## Casos de uso extraidos

1. **Registrarse con la cuenta de Google**
    * Un usuario anónimo (no registrado) accede a la app. La app le ofrece la opcion de iniciar sesión con la cuenta de google. El usuario anónimo escoge iniciar sesión con la cuenta de google y entonces la aplicación le redirige al sistema de autenticación de Google, donde le pedirá permisos para ver su calendario. Una vez completado el registro el sistema le redirige a la pantalla principal con la sesion iniciada.

2. **Iniciar sesión con la cuenta de Google**
   * Un usuario registrado accede a la aplicación. La aplicación le ofrece las opciones de iniciar sesión con su cuenta de Google o registrarse (para usuarios no registrados). El usuario registrado selecciona iniciar sesión con su cuenta de Google y entonces la aplicación le redirige al sistema de autenticación de Google. Una vez completado el inicio de sesión el sistema le redirige a la pantalla principal con la sesion iniciada.

3. **Configurar el tiempo de trabajo**
   * Un usuario con la sesión iniciada accede a la página de configuración de su perfil. La aplicación muestra las opciones para editar las horas de trabajo. El usuario indica que quiere editar sus horas de trabajo, introduce las nuevas horas y confirma los cambios. La aplicación actualiza el perfil del usuario con las nuevas horas de trabajo.

4. **Seleccionar calendarios para cálculos**
   * Un usuario con la sesión iniciada accede a la página de configuración de su perfil. La aplicación muestra las opciones para seleccionar el calendario a utilizar. El usuario indica que quiere seleccionar el calendario, elige el que quiere utilizar y confirma su selección. La aplicación actualiza el perfil del usuario con el calendarios seleccionado.

5. **Seleccionar el periodo temporal para los cálculos**
   * Un usuario con la sesión iniciada accede a la página de configuración de su perfil. La aplicación muestra las opciones para seleccionar el periodo temporal a utilizar. El usuario indica que quiere seleccionar el periodo temporal, elige el que quiere utilizar y confirma su selección. La aplicación actualiza el perfil del usuario con el periodo temporal seleccionado.

6. **Ver el tiempo gastado en reuniones**
   * Un usuario con la sesión iniciada accede a la página de visualización de datos. La aplicación muestra la cantidad de tiempo que el usuario ha gastado en reuniones tanto en texto como en una gráfica.

7. **Ver el porcentaje de tiempo gastado en reuniones y el porcentaje de tiempo libre**
   * Un usuario con la sesión iniciada accede a la página de visualización de datos. La aplicación muestra el porcentaje de tiempo que el usuario ha gastado en reuniones y el porcentaje de tiempo libre, ambos representados tanto en texto como en una gráfica.

8. **Configurar el tiempo entre reuniones que cuenta como tiempo de reunión**
   * Un usuario con la sesión iniciada accede a la página de configuración de su perfil. La aplicación muestra las opciones para configurar el tiempo entre reuniones que cuenta como tiempo de reunión. El usuario indica que quiere configurar este tiempo, selecciona el intervalo que quiere que cuente como tiempo de reunión y confirma su selección. La aplicación actualiza el perfil del usuario con el nuevo intervalo de tiempo entre reuniones.

### Caso de uso: Configurar el tiempo de trabajo
#### Escenario principal:

1. El usuario inicia la edición de sus horas de trabajo.
2. El sistema solicita la información requerida para esta edición: las horas de trabajo del usuario.
3. El usuario introduce la información requerida.
4. El sistema registra las nuevas horas de trabajo y las añade al perfil del usuario.
5. El sistema muestra al usuario la confirmación de que las horas de trabajo se han actualizado.

#### Escenarios alternativos

 * 1-3a. El usuario cancela la edición
      
      1. El usuario solicita al sistema que cancele la edición de las horas de trabajo.
      2. El sistema pide confirmación.
      3. El usuario confirma.
      4. El sistema muestra la configuración del perfil del usuario sin cambios.
 * 4a. El usuario introduce un formato incorrecto de horas de trabajo

      1. El sistema solicita de nuevo la información faltante, especificando el formato correcto de las horas de trabajo.
      2. El usuario...
         1. Introduce las horas de trabajo en el formato correcto.
         2. Abandona la operación.

### Caso de uso: Ver el tiempo gastado en reuniones
#### Escenario principal:

1. El usuario con la sesión iniciada accede a la página de visualización de datos.
2. El sistema recopila y calcula el tiempo total gastado en reuniones.
3. El sistema muestra al usuario la cantidad de tiempo que ha gastado en reuniones en texto y en una gráfica.

#### Escenarios alternativos:

* 2a. No hay datos de reuniones disponibles.

   1. El sistema identifica que no hay datos de reuniones disponibles para el usuario.
   2. El sistema muestra un mensaje informando al usuario de la falta de datos y sugiere posibles soluciones (por ejemplo, esperar a tener reuniones, etc.).

 * 2b. Error al recopilar o procesar los datos.

   1. El sistema identifica que ha ocurrido un error al recopilar o procesar los datos de las reuniones.
   2. El sistema muestra un mensaje de error al usuario indicando que ha habido un problema al procesar los datos y que lo intentará de nuevo más tarde.