# Time is Money

## Charakterystyka oprogramowania.
Aplikacja TimeIsMoney służy do śledzenia postępów i czasu wykonania danej pracy.

Aplikacja jest wykonana w android studio. Dane użytkowników i dodane notatki znajdują się w bazie danych Firebase.
## Prawa autorskie.
1. Marta Miklaszewska, Magdalena Retzlaff, Oliwia Wielgus
2. MIT
## Specyfikacja wymagań.
1. Wymagania funkcjonalne:
* Śledzi czas;
* Pokazuje przepracowane godziny;
* Pomaga w motywacji;
* Pomaga w przejrzystym przeglądzie własnej pracy;

2. Wymagania niefunkcjonalne.
* Zadowolenie klientów
## Architektura systemu/oprogramowania.
1. Architektura rozwoju.
* Utowrzenie projektu i podłączenie go do Firebase
* Utworzenie logowania
* Przyjmowanie danych i śledzenie ich w czasie
* Funkcja śledzenia czasu
* Stworzenie przyjaznego stylistycznie interfejsu
2. Architektura uruchomieniowa.
* Android Studio
* Firebase
* Java
## Testy
### Scenariusze testów.
#### Zalogowanie się lub zarejestrowanie przy użyciu emaila:
1. Wpisanie adresu email.
2. Jeśli użytkownik logował się wcześniej: Wpisanie hasła.
3. Jeśli to pierwsze logowanie: Podanie nazwy użytkownika i hasła.
#### Oczekiwany rezultat: Użytkownik zalogował się do systemu.



#### Zalogowanie się przy użyciu numeru telefonu:
1. Podanie numeru telefonu.
2. Wpisanie kodu otrzymanego w wiadomości sms.
#### Oczekiwany rezultat: Użytkownik zalogował się do systemu.

#### Dodanie wpisu:
1. Naciśnięcie przycisku w prawym dolnym rogu.
2. Podanie treści wpisu i czasu.
3. Zatwierdzenie wpisu.
#### Oczekiwany rezultat: Wpis pojawił się na ekranie głównym.

Edycja profilu:
1. Otworzenie aparatu po naciśnięciu na pole zdjęcia.
2. Wprowadzenie nazwy użytkownika.
3. Zatwierdzenie zmian.

#### Oczekiwany rezultat: Profil został zaktualizowany.
