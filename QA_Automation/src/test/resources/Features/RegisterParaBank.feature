Feature: Registro de usuarios de la pagina Parabank
  Esta pagina va a ser usada para testear con assertions de JUnit
  @test
  @SmokeTest
  Scenario: Validar texto
    Given El cliente ingresa al sitio principal https://parabank.parasoft.com/parabank/register.htm
    When Cargo la informacion del DOM registerParaBank.json
    And Configuro el elemento FirstName con el texto Leandro
    And Configuro el elemento LastName con el texto Gomez
    And Configuro el elemento Address con el texto white city 67
    And Configuro el elemento City con el texto Melbourne
    And Configuro el elemento State con el texto Buenos Aires
    And Configuro el elemento ZipCode con el texto 5445
    And Configuro el elemento Phone con el texto 0606456
    And Configuro el elemento SSN con el texto 188
    And Configuro el elemento Password con el texto 88ss8d8ss
    And Configuro el elemento ConfirmPass con el texto 88ss8d8ss
    And Hago clic en el elemento RegisterButton
    Then Confirmo si el UsernameIsRequired contiene el texto Username is required.
    And Tomo captura de pantalla: verificar mensaje
    #Then Confirmo si el UsernameIsRequired es igual a Username is required.
    #Then Confirmar si muestra el elemento UsernameIsRequired
