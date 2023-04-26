Feature: Registrar user
  Se probara el registro de usuarios y se validaran los campos del formulario
  @test
  @SmokeTest
  Scenario: Crear cuenta nueva
    Given El cliente se encuentra en la pantalla de logeo
    Then Cargo la informacion del DOM registro.json
    And Hago clic en el elemento Email
    And Configuro el elemento Email con el texto leo.moren@gmail.com
    And Hago clic en el elemento Button
    And Marco la casilla de verificacion que tiene Mr.
    And Hago clic en el elemento First Name
    And Configuro el elemento First Name con el texto Leandro
    And Hago clic en el elemento Last Name
    And Configuro el elemento Last Name con el texto Moren
    And Hago clic en el elemento Email_registro
    And Elimino texto del elemento Email_registro
    And Configuro el elemento Email_registro con el texto leo.moren@gmail.com
    And Hago clic en el elemento Password
    And Configuro el elemento Password con el texto 1234
    #And Hago clic en el elemento T-Shirts
    And Me desplazo hasta end de la pagina
    #And Me desplazo al elemento Register_button
