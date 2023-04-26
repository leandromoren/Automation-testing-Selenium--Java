Feature: Login con usuario y contrasenia
  Descripcion opcional de la funcion
  @test
  @login
  Scenario: Cliente se logea en la pagina principal ingresando su contrasenia incorrecta.
    Given  El cliente se encuentra en la pantalla de logeo
    Then Cargo la informacion del DOM login.json
    And Hago clic en el elemento Email
    And Configuro el elemento Email con el texto leandro.moren18@gmail.com
    And Tomo captura de pantalla login

  @test
  Scenario: Menu desplegable de control
    Given El cliente ingresa al sitio principal http://automationpractice.pl/index.php?controller=contact
    Then Cargo la informacion del DOM contactenos.json
    And Configuro el texto Customer service en el menu desplegable TIPO DE ATENCION
    And Configuro el indice 1 en el menu desplegable TIPO DE ATENCION

  @test
  Scenario: Manejar multiples ventanas
    Given El cliente ingresa al sitio principal http://automationpractice.pl/index.php?controller=authentication&back=my-account
    And Abro una nueva ventana con URL http://automationpractice.pl/index.php?controller=contact
    And Cambio a una nueva ventana
    And Cargo la informacion del DOM contactenos.json
    And Configuro el texto Customer service en el menu desplegable TIPO DE ATENCION
    Then Configuro el indice 1 en el menu desplegable TIPO DE ATENCION
    And Voy a la ventana Principal
    
  @test
  Scenario: Manejar alertas
    Given El cliente ingresa al sitio principal https://www.anerbarrena.com/demos/2017/004-ejemplo-javascript-confirm-js.php
    Then Cargo la informacion del DOM alert.json
    And Hago clic en el elemento Mostrar Alerta
    Then Yo accept la alerta