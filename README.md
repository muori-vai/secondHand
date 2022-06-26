# Secondhand
Progetto di gruppo per Sistemi Informativi sul Web

(Sono riportati solo gli scenari principali di successo)

Caso d'uso 1: Inserimento Prodotto; attore primario: User/Admin
  1. Utente si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale.
  3. Utente sceglie l'operazione "Inserisci Prodotto".
  4. Utente inserisce i dati del Prodotto.
  5. Il Sistema salva il Prodotto.
 
Caso d'uso 2, 3, 4: Inserimento Categoria, Sottocategoria, Luogo; attore primario: Admin
  1. Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale.
  3. Admin sceglie l'operazione "Visualizza Categorie/Sottocategorie/Luoghi".
  4. Il Sistema mostra l'elenco di Categorie/Sottocategorie/Luoghi.
  5. Admin sceglie l'operazione "Inserisci Categoria/Sottocategoria/Luoghi".
  6. Admin inserisce i dati di Categoria/Sottocategoria/Luogo.
  7. Il Sistema salva Categoria/Sottocategoria/Luogo.

Caso d'uso 5: Cancellazione Prodotto; attore primario: User/Admin
  1. Utente si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale.
  3. Utente sceglie l'operazione "Visualizza Prodotto".
  4. Il Sistema mostra i dettagli del Prodotto.
  5. Utente sceglie l'operazione "Cancella Prodotto" (disponibile solo se l'attore è Admin oppure è lo User proprietario del prodotto).
  6. Il Sistema cancella il Prodotto.

Caso d'uso 6: Modifica Prodotto; attore primario: User/Admin
  1. Utente si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale.
  3. Utente sceglie l'operazione "Visualizza Prodotto".
  4. Il Sistema mostra i dettagli del Prodotto.
  5. Utente sceglie l'operazione "Modifica Prodotto" (disponibile solo se l'attore è Admin oppure è lo User proprietario del prodotto).
  6. Utente inserisce i nuovi dati del Prodotto.
  7. Il Sistema aggiorna il Prodotto con i nuovi dati.

Caso d'uso 7, 8, 9: Cancellazione Categoria, Sottocategoria, Luogo; attore primario: Admin
  1. Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale.
  3. Admin sceglie l'operazione "Visualizza Categorie/Sottocategorie/Luoghi".
  4. Il Sistema mostra l'elenco di Categorie/Sottocategorie/Luoghi.
  5. Admin sceglie l'operazione "Visualizza Categoria/Sottocategoria/Luogo".
  6. Il Sistema mostra i dettagli di Categoria/Sottocategoria/Luogo.
  7. Admin sceglie l'operazione "Cancella Categoria/Sottocategoria/Luogo".
  9. Il Sistema cancella Categoria/Sottocategoria/Luogo.

Caso d'uso 10, 11, 12: Modifica Categoria, Sottocategoria, Luogo; attore primario: Admin
  1. Admin si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale.
  3. Admin sceglie l'operazione "Visualizza Categorie/Sottocategorie/Luoghi".
  4. Il Sistema mostra l'elenco di Categorie/Sottocategorie/Luoghi.
  5. Admin sceglie l'operazione "Visualizza Categoria/Sottocategoria/Luogo".
  6. Il Sistema mostra i dettagli di Categoria/Sottocategoria/Luogo.
  7. Admin sceglie l'operazione "Modifica Categoria/Sottocategoria/Luogo".
  8. Admin inserisce i nuovi dati di Categoria/Sottocategoria/Luogo.
  9. Il Sistema aggiorna Categoria/Sottocategoria/Luogo con i nuovi dati.
 
 Caso d'uso 13, 14, 15, 16: Visualizzazione Prodotto, Categoria, Sottocategoria, Luogo; attore primario: NonAutenticato/User/Admin
  1. Utente si fa autenticare dal Sistema tramite Username e Password.
  2. Il Sistema mostra la pagina principale.
  3. Utente sceglie l'operazione "Visualizza Prodotti/Categorie/Sottocategorie/Luoghi".
  4. Il Sistema mostra l'elenco di Prodotti/Categorie/Sottocategorie/Luoghi.
  5. Utente sceglie l'operazione "Visualizza Prodotto/Categoria/Sottocategoria/Luogo".
  6. Il Sistema mostra i dettagli di Prodotto/Categoria/Sottocategoria/Luogo.
