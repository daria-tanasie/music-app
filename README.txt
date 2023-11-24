Fisierul Main.java:
        In acest fisier am facut doar citirea librariei cu useri, melodii si podcasturi.
    Am citit apoi comenzile de intrare si am definit o instanta a clasei Menu care este construita
    pe baza comenzilor citite, a fisierului de iesire si a librariei. Apelam apoi functia actionsSpotify() din
    clasa Menu.

Fisierul Menu.java:
        Aici se vor parsa comenzile primite cu ajutorul unui switch in functia actionsSpotify().
     Vom itera prin comenzile primite ca input, iar daca se gaseste comanda specificata, se intra pe ramura
     switchului.

Fisierul search_bar/Search.java:
        Daca este data comanda de search, cream o instanta a clasei Search si apelam metoda
     execute din clasa Search. Aici vom verifica ce tip de audio se cauta. Daca este song, vom
     itera prin toate melodiile din librarie. Cu ajutorul functiei nrFilters() vom afla nr de
     filtre dupa care se cauta melodia. Vom verifica apoi ce fel de filtre are search ul si vom
     verifica pentru fiecare filtru si melodie daca satisface cerintele. Cu ajutorul lui cnt nu vom afisa
     mai mult de 5 elemente gasite. De fiecare data cand gasim o melodie o adaugam la array ul currentCommand.results
     care se va afisa la sfarsit. Daca vrem sa cautam un podcast, se va intra pe ramura de podcast, cautandu se doar
     dupa 2 filtre. Daca gasim un podcast vom adauga la outputul final cu functia setCmdPodcast(). Ultimul tip de audio
     este Playlistul, cautand din nou dupa 2 filtre, asemanator cu podcast. La final vom seta mesajul cu nr de gasiri,
     iar pentru fiecare tip de audio avem o metoda de set care satisface modelul outputului.
        Se revine in menu, iar la fiecare search, playerul se opreste, iar variabila loaded va deveni false, iar timePassed
     se va actualiza in functie de starea lui paused la care vom ajunge mai traziu. In plus currentAudio si selectedPlaylist
     sunt nule pana la select/laod.


Fisierul search_bar/Select.java:
        Daca vom avea comanda Select, cream o instanta a clasei Select si apelam metoda execute ce intoarce audio ul
     selectat. In aceasta metoda verificam intai daca sunt satisfacute cerintele de select, iar daca nu, se da mesajul si
     se revine in Menu.
        Daca sunt satisfacute cerintele, vom prelua audio ul aflat pe pozitia getItemNUmber si il vom returna.
        Cand se revine in Menu vom verifica ce tip de audio avem, iar daca este podcast, vom cauta podcstul respectiv pentru a
     da play la episoade, cautand si episodul la care s a ramas daca a fost inceput podcstul.
        In caz ca avem un playlist, il cautam in array ul de playlisturi si currentAudio devine prima melodie din
     playlist, iar selectedPlaylist este playlistul care ruleaza.

Fisierul player/Load.java:
        Daca se da comanda load, intram pe aceasta ramura a switchului de unde se apeleaza metoda execute din clasa Load.
     Inante de asta vom seta repeat pe "No Repeat" si repeatCode ul pe 0.
        In metoda execute vom verifica daca sunt satisfacute cerintele pt load, iar in caz contrar se seteaza mesajul si
     se iese din metoda. Daca sunt satisfacute conditiile se seteaza mesajul si se revine in Menu. Daca currentAudio nu este
     null, loaded devine true si se incepe redarea audio ului, iar paused devine false.

Fisierul player/PlayPause.java:
       Pentru aceasta comanda vom executa metoda execute din clasa PlayPause. Aici se verifica cerinta sa fie loaded = true
     pentru a se executa comanda, iar in caz contrar se seteaza mesajul si se iese din metoda. In caz ca loaded este true,
     se va seta mesajul in functe de starea actuala a lui paused si se iese din metoda dupa setarea outputului.
        La revenirea in Menu, in functie de starea lui paused, se actualizeaza timePassed.

Fisierul player/Status.java :
        Se executa metoda execute din clasa Status. Daca nu este nimic in load, se seteaza outputul si se iese din
     metoda. Daca este o melodie, vom actualiza datele despre timpul trecut si cel ramas, precum si pentru repeat, in
     functie de durata si timestamp, iar apoi setam outputul si iesim din metoda.
        Daca este podcast, vom cauta episodul la care ne aflam, iar daca s a terminat cel anterior, trecem la urmatorul.
        Daca vom avea playlist, se cauta melodia la care ne aflam, iar functie de repeat si duration setam outputul, trecand
     la urmatoarea melodie daca este cazul si daca nu suntem la sfarsit. La revenire in Menu vom seta si repeatCode ul la
     care vom ajunge mai tarziu.

Fisierul playlist_comm/Playlists.java:
        Am creat acest fisier pentru a pastra toate playlisturile create. In clasa Playlists avem fieldurile pentru
     name, owner, id, melodiile continute, visibility, followers.
        In plus, avem metoda showPlaylists() care se va apela cand un user vrea sa isi vada playlisturile. In aceasta
     metoda cautam prin useri pana il gasim pe cel curent, apoi adaugam toate playlisturile intr un Array format din elemente
     de tip clasa Details, care va pastra detaliile despre playlist. La final setam outputul si se iese din metoda.

Fisierul playlist_comm/CreatePlaylist.java:
         Se apeleaza comanda execute() din clasa CreatePlaylist care va returna true/false in functie de reusita comenzii.
     In metoda execute() se cauta userul curent si se verifica daca exista deja un playlist cu numele dat, iar daca da, ok
     devine false, altfel ramane true. Daca nu exista un astfel de playlist se creeaza si se da mesaj, altfel se da mesaj ca
     exista deja.
          La revenire in Menu, se verifica daca s a reusit crearea playlistului, iar daca da, se adauga la Array ul de
     playlisturi existente si la cele ale userului curent.

Fisierul playlist_comm/AddRemoveInPlaylist:
        Se executa metoda execute() din aceasta clasa. Intai se verifica daca melodia care se doreste a fost loaded, iar in caz
     contrar se iese din metoda si se seteaza mesajul. Altfel, se cauta user ul curent si daca are un playlist cu id ul
     dat. Daca nu, se seteaza mesajul si se iese din metoda. In sens contrar, se cauta melodia ce se doreste a fi adaugata.
     Daca exista, aceasta va fi removed din playlist, iar daca nu se adauga si se seteaza mesajele corespunzatoare.
        La final daca nu se gaseste o melodie in songs, inseamna ca sursa nu este o melodie si nu se adauga la playlist.

Fisierul playlist_comm/LikeUnlike.java:
         Se executa metoda execute din aceasta clasa. Se verifica daca este o sursa loaded, iar in caz contrar se seteaza
     mesajul si se iese. Se cauta user ul curent, iar daca nu a mai dat like la nimic se initiaza un array de likedSongs
     din clasa Users unde vor fi adaugate melodiile la care el da like.
          Se cauta apoi prin melodii cea curenta si apoi se verifica daca a dat deja like la ea. Daca a dat like la melodie,
     se va da unlike, se da remove la melodie din likedSongs si va fi setat mesajul, iar apoi se iese din metoda. In sens contrar,
     se da like si se adauga melodia la likedSongs. Daca se ajunge la final, inseamna ca sursa nu este melodie si se iese.

Fisierul data/Users.java:
        Aici se afla clasa Users, care are fieldurile username, age, city, playlists, unde se regasesc playlisturile sale,
     likedSongs despre care am vorbit mai devreme si following unde se vor afla playlisturile la care da follow un utilizator.
        Pentru comanda showPreferredSongs se executa metoda execute() din users. Aici se cauta userul curent si se adauga la
     arrayul result toate melodiile la care a dat like utilizatorul si se iese.

Fisierul player/Repeat.java:
        Pentru comanda repeat se apeleaza metoda execute() din clasa Repeat. Aici se verifica daca a fost loaded o sursa.
     In caz contrar, se seteaza mesajul si se iese din metoda. Vom avea 2 optiuni prin care vom stii starea lui repeat:
     un cod corespunzator starii si un string ce ne ajuta la output. De fiecare data cand este data comanda de repeat,
     repeatCode ul creste. Dupa, cu ajutorul unui switch, vom afla stringul pe baza code ului. La final, setam mesajul
     si iesim din metoda. Pe langa metoda execute avem si metoda getRepeat() carepe baza code ului va returna un string
     ce va ajuta la setarea statusului. Apoi avem metoda getRepeatCode() care pe baza stringului va returna codeul.

Fisierul playlist_comm/Follow.java:
        Aici verificam daca a fost selectat un playlist inainte de aceasta comanda. Cautam apoi userul curent. Dupa,
      cautam in playlisturi pe cel curent si vrificam daca acesta se afla printre playlisturile userului curent. Daca da,
      se da mesaj de eroare si se iese. In caz contrar, se verifica daca acest playlist se afla deja la following, iar daca
      se afla se da unfollow si remove. Daca nu se afla, se da follow si se adauga. Daca se ajunge la final, inseamna ca sursa
      nu este playlist.

Fisierul playlist_comm/SwitchVisibility.java:
        Pt aceasta comanda se executa metoda execute() din clasa. Aici se cauta userul, iar apoi se verifica daca exista
      playlistul cu id ul specificat. Daca da, se verifica starea playlistului(private/public), iar in functie de aceasta,
      se schimba cu cea opusa si se seteaza mesajul.

Fisierul trending/GetTop5.java:
        In aceasa clasa se afla metodele care se executa pentru comenzile getTop5Playlists si getTop5Songs. Pentru
      getTop5Playlists avem metoda executeP(). Aici vom sorta arrayul de playlists unde se afla toate playlisturile
      create in functie de nr de followeri, iar apoi le afisam pe primele 5.
        Pentru comanda getTop5PSongs se executa metoda executeS(). Aici se pun in array ul sortedSongs cele care
      au mai mult de 0 likeuri si apoi se sorteaza sortedSongs dupa nr de likeuri. La final, daca nu exista 5 melodii
      cu mai mult de 0 likeuri, se completeaza cu cele cu 0 si se pun in array ul result cele 5.

Fisierul player/NextPrev.java:
        In aceasta clasa vom avea metoda executePrev() ce se apeleaza la comanda "prev". Daca playlistul este null,
      se seteaza mesajul si se iese din metoda. Altfel, se cauta melodia curenta in playlist, iar apoi se verifica daca mai
      sunt melodii anterioare sau daca a trecut mai mult de o secunda de la inceperea melodiei. In caz afirmativ, se iese din
      loop si se seteaza mesajul, playerul ramanand la melodia curenta. Daca totusi n a trecut o secunda si mai sunt melodii in
      spate, se trece la cea anterioara.
        Metoda execute next se apeleaza pt comanda "next". Daca avem un playlist, se cauta acesta si apoi se verifica daca
      mai exista o melodie dupa el, iar in caz afirmativ, se trece la urmatoarea. Daca in schimb avem un podcast, vom
      cauta episodul curent si daca exista un episod dupa el, trecem la urmatorul.
        La intoarcerea in main, pt fiecare comanda vom reseta timpul trecut (timePassed) daca next sau prev intorc null.

Fisierul player/ForwardBackward.java:
        In acest fisier avem metoda executeFw() care se executa la comanda forward. Verificam intai daca avem o sursa loaded,
      iar in caz ca nu se seteaza mesajul. Daca avem o sursa, verificam sa fie podcast si cautam episodul, iar apoi setam
      timpul cu 90 de secunde mai tarziu si il returnam. Daca nu este podcast, se seteaza mesajul si se iese.
        Mai avem metoda executeBw() care se apeleaza pentru comanda backward. Se verifica sa fie o sursa loaded ca mai devreme.
      Se verifica apoi sa fie podcast si cautam episodul. Setam apoi timpul cu 90 de secunde inapoi si il returnam. In cazul
      in care nu avem un podcast, se seteaza mesajul si se iese.

        La final in clasa Menu, mai avem doar de afisat toate comenzile cu ajutorul metodei writeFile() care afiseaza
      comenzile cu ajutorul lui objectWriter. La fiecare comanda se adauga la array ul de comenzi de output
      (commandsOutput) comanda curenta pt a fi afisata la final.

        Mai avem in Menu metode ce ne adauga un plalist la playlisturile unui user(addPlaylist()), verifica daca sursa
      este un podcast(isPodcast()), sau daca e playlist(isPlaylist()).

Fisierul commands/CommandsInput.java:
         Cu ajutorul acestei clase vom realiza citirea comenzilor si a continutului lor in fieldul
      specific al clasei.

Fiserul commands/CommandsOutput.java:
         Cu ajutorul acestei clase vom realiza scriere, fiecarui field fiind atribuit outputul comenzii.
       La afisare, cele nule sunt ignorate cu @JsonInclude(JsonInclude.Include.NON_NULL).

    In packageul data avem toate tipurile de entitati: episoade, podcasturi, melodii, libraria si userii unde
se va realiza citirea si atribuirea in functie de fielduri.