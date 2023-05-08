# Requisitos funcionales
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

## Casos de uso extraidos

1. Como usuario quiero registrarme en la app con mi cuenta de Google
   * Dado un usuario sin registrar
   * Cuando acceda a la app 
   * Y elija registrarse 
   * Entonces se registrará en la app a través de Google 
2. Como usuario ya registrado quiero iniciar sesión con mi cuenta de Google
   * Dado un usuario ya registrado 
   * Cuando acceda a la app 
   * Y elija iniciar sesión con Google 
   * Entonces iniciará sesión en la app a través de Google 
3. Como usuario quiero configurar mi tiempo de trabajo
   * Dado un usuario con la sesión iniciada 
   * Y en la página de configuración de su perfil 
   * Cuando indique que quiere editar sus horas de trabajo 
   * Y introduzca sus horas de trabajo 
   * Y confirme los cambios 
   * Entonces la app reflejará sus nuevas horas de trabajo 
4. Como usuario quiero seleccionar qué calendarios se tendrán en cuenta para los cálculos
   * Dado un usuario con la sesión iniciada 
   * Y en la página de configuración de su perfil 
   * Cuando indique que quiere seleccionar los calendarios a utilizar 
   * Y seleccione los calendarios que quiere utilizar 
   * Y confirme su selección 
   * Entonces la app reflejará el cambio en los calendarios usados para realizar los cálculos 
5. Como usuario quiero seleccionar qué periodo temporal se usará para realizar los cálculos
   * Dado un usuario con la sesión iniciada 
   * Y en la página de configuración de su perfil 
   * Cuando indique que quiere seleccionar el periodo temporal a utilizar 
   * Y seleccione el periodo temporal que quiere utilizar 
   * Y confirme su selección 
   * Entonces la app reflejará el cambio en el periodo temporal usado para realizar los cálculos. 
6. Como usuario quiero ver cuánto tiempo he gastado en reuniones
   * Dado un usuario con la sesión iniciada 
   * Y en la página de visualización de datos 
   * Cuando acceda a la página 
   * Entonces verá la cantidad de tiempo que ha gastado en reuniones en texto 
   * Y verá ese tiempo reflejado en una gráfica. 
7. Como usuario quiero ver el porcentaje de tiempo gastado en reuniones y el porcentaje de tiempo libre
   * Dado un usuario con la sesión iniciada 
   * Y en la página de visualización de datos 
   * Cuando acceda a la página 
   * Entonces verá el porcentaje de tiempo que ha gastado en reuniones 
   * Y verá el porcentaje de tiempo libre 
   * Y verá estos porcentajes reflejados en una gráfica. 
8. Como usuario quiero configurar el tiempo entre reuniones que cuenta como tiempo de reunión
   * Dado un usuario con la sesión iniciada 
   * Y en la página de configuración de su perfil 
   * Cuando indique que quiere configurar el tiempo entre reuniones que cuenta como tiempo de reunión 
   * Y seleccione el tiempo que quiere que cuente como tiempo de reunión 
   * Y confirme su selección 
   * Entonces la app tendrá en cuenta el tiempo seleccionado entre reuniones como tiempo de reunión al realizar los cálculos. 
