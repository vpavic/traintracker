package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

class HzppSampleResponses {

    static final String currentPositionOk = """

            <!-- saved from url=(0087)http://vred.hzinfra.hr/hzinfo/Default.asp?VL=2010&Category=hzinfo&Service=tpvl&SCREEN=2 -->
            <html><head><meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
            <title>Trenutna pozicija vlaka</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="windows-1250&quot;">
            </head>
            <body background="./Trenutna pozicija vlaka_files/slika.jpg"><table align="CENTER"><tbody><tr>
            <td><font color="#333399"><font face="Verdana,Arial,Helvetica" color="#333399">
            <h3 align="center">HŽ Infrastruktura<br>                                  </h3></font>
            </font></td></tr></tbody></table>
            <hr>
            <form method="GET" action="http://vred.hzinfra.hr/hzinfo/Default.asp?">
            <p align="CENTER">
            <font size="6" face="Arial,Helvetica" color="#333399">
            <table align="CENETR" width="110%">
            <tbody><tr><td bgcolor="#bbddff"><i>Trenutna pozicija<br>vlak: </i>  2010 <br>
            Relacija:<br> VINKOVCI--&gt;ZAGREB-GLA </td></tr><tr>
            <td bgcolor="#bbddff"><i>Kolodvor: </i><strong>NOVA+KAPELA+BATRINA<br> </strong></td></tr><tr>
            <td bgcolor="#bbddff"><i>Odlazak  </i><cr>
            17.09.18. u 05:43 sati</cr></td></tr><tr>
            <td><font face="Arial,Helvetica" color="#FF000A">
            <blink>Kasni   27 min.                                   </blink><br>
            <font size="4" face="Verdana,Arial,Helvetica" color="#333399">
            Vlak se očekuje<br>u kolodvoru: ZAPOLJE           <br>
            </font></font></td></tr><tr><td>
            </td></tr></tbody></table></font></p><hr><font size="6" face="Arial,Helvetica" color="#333399"><font size="1" face="Arial,Helvetica" color="009FFF">
            Stanje vlaka od 17/09/18   u 05:54   <hr>
            <input type="HIDDEN" name="Category" value="hzinfo">
            <input type="HIDDEN" name="Service" value="tpvl">
            <input type="HIDDEN" name="SCREEN" value="1">
            <input type="SUBMIT" value="Povrat">



            </font></font></form></body></html>
            """;

    static final String currentPositionNotFound = """

            <!-- saved from url=(0086)http://vred.hzinfra.hr/hzinfo/Default.asp?VL=209&Category=hzinfo&Service=tpvl&SCREEN=2 -->
            <html><head><meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
            <meta name="viewport" content="width=device-width, initial-scale=1.0" charset="windows-1250&quot;">
            <title>Trenutna pozicija putničkog vlaka</title>
            </head>
            <body background="./Trenutna pozicija putničkog vlaka_files/slika.jpg"><table align="CENTER"><tbody><tr>
            <td><font color="#333399"><font face="Verdana,Arial,Helvetica" color="#333399">
            <h5 align="center">HŽ Infrastruktura<br>Trenutna pozicija putničkog vlaka</h5></font>
            </font></td></tr></tbody></table>
            <hr>
            <form method="GET" action="http://vred.hzinfra.hr/hzinfo/Default.asp?"><p><font face="Arial,Helvetica" color="#333399" align="center">
            <strong>Broj vlaka: </strong>
            <input name="VL" type="TEXT" size="5" maxlength="5">
            </font></p><p><font face="Arial,Helvetica" color="#333399" align="center">
            </font></p><p><font face="Arial,Helvetica" color="#333399" align="center"><strong>Vlak nije u evidenciji.                                     </strong></font></p><font face="Arial,Helvetica" color="#333399" align="center">
            <input type="HIDDEN" name="Category" value="hzinfo">
            <input type="HIDDEN" name="Service" value="tpvl">
            <input type="HIDDEN" name="SCREEN" value="2">
            <input type="SUBMIT" value=" OK ">

            <pre><p>
            <strong></strong></p><p><strong>
            <strong></strong></strong></p><p><strong><strong>
            <strong></strong></strong></strong></p><p></p></pre><strong><strong><strong>


            </strong></strong></strong></font></form></body></html>
            """;

    static final String overviewOk = """

            <!-- saved from url=(0126)http://najava.hzinfra.hr/hzinfo/default.asp?VL=211&D1=161203&Kaj=Trazi++&Category=KORISNICI&Service=PKVL&SCREEN=2&LANG=HR&ot=1 -->
            <html><head><meta http-equiv="Content-Type" content="text/html; charset=windows-1250">
            <title>Prikaz podataka o kretanju vlaka</title>

            </head>
            <body><form method="GET" action="http://najava.hzinfra.hr/hzinfo/default.asp?">
            <table align="CENTER">
            <tbody><tr><td><font color="#000000" face="Arial">
            <h3 align="center">Pregled kretanja vlaka - Prikaz podataka o kretanju vlaka   211 od dana 161203 </h3></font>
            </td></tr></tbody></table><hr><table align="CENTER">
            <tbody><tr><td><font color="#000000" face="Arial" style="height:25px;font-size:18px;">
            Vlak   211 imao je posljednju akciju Dolazak   u/iz kolodvora VINKOVCI             dana 03.12.2016. u
            21:28 sati.</font></td>
            </tr></tbody></table><table align="center" border="1" cellspacing="1" cellpadding="1" width="100%" bgcolor="#C0C0C0">
            <tbody><tr><th align="CENTER"><font color="#000000" face="Arial" size="3">Kolodvor</font></th>
            <th align="CENTER"><font color="#000000" face="Arial" size="3">Dolazak/Odlazak</font></th>
            <th align="CENTER"><font color="#000000" face="Arial" size="3">Datum</font></th>
            <th align="CENTER"><font color="#000000" face="Arial" size="3">Sat</font></th>
            <th align="CENTER"><font color="#000000" face="Arial" size="3">Kasni</font></th></tr><tr>
            <td align="left" bgcolor="#ccffff"><font face="Arial" size="3">SAVSKI MAROF GRANICA</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">17:14</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  30</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">SAVSKI MAROF        </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">17:17</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  29</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">ZAPRE?I?            </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">17:24</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  29</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">PODSUSED tvornica   </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">17:30</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  29</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">ZAGREB ZAP. KOL.    </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">17:38</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  29</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">ZAGREB GL. KOL.     </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">17:41</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  29</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">ZAGREB GL. KOL.     </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">18:03</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  21</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">ZAGREB BORONGAJ     </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">18:07</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  20</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">SESVETE             </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">18:13</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  21</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">DUGO SELO           </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">18:20</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">DUGO SELO           </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">18:21</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">PRECEC--------------</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">18:32</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">IVANI? GRAD         </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">18:44</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  25</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">IVANI? GRAD         </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">18:45</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  25</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">DEANOVEC            </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">18:51</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  25</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">NOVOSELEC           </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">18:59</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  24</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">LUDINA              </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">19:05</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">POPOVA?A            </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">19:12</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">MOSLAVA?KA GRA?ENICA</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">19:20</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">KUTINA              </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">19:27</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">KUTINA              </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">19:28</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">BANOVA JARUGA       </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">19:36</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  21</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">BANOVA JARUGA       </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">19:37</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  21</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">LIPOVLJANI          </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">19:43</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  20</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">NOVSKA              </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">19:52</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  19</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">NOVSKA              </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">19:53</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  19</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">OKUČANI             </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">20:07</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">DRAGALIĆ            </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">20:11</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">NOVA GRADIŠKA       </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">20:16</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">NOVA GRADIŠKA       </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">20:17</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">STARO PETROVO SELO  </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">20:25</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  24</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">NOVA KAPELA-BATRINA </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">20:31</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  24</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">NOVA KAPELA-BATRINA </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">20:32</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  24</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">ORIOVAC             </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">20:37</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">KUTI                </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">20:41</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  24</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">SIBINJ              </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">20:45</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">SLAVONSKI BROD      </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">20:54</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">SLAVONSKI BROD      </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">20:56</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  23</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">GARČIN              </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">21:03</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">ANDRIJEVCI          </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">21:07</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">STRIZIVOJNA VRPOLJE </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">21:12</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">STRIZIVOJNA VRPOLJE </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">21:13</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  22</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">STARI MIKANOVCI     </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">21:18</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  21</font></td></tr>
            <tr><td align="left" bgcolor="#00ccff"><font face="Arial" size="3">IVANKOVO            </font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">Odlazak</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">21:23</font></td>
            <td align="CENTER" bgcolor="#00ccff"><font face="Arial" size="3">  21</font></td></tr>
            <tr><td align="left" bgcolor="#ccffff"><font face="Arial" size="3">VINKOVCI            </font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">Dolazak</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">03.12.2016</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">21:28</font></td>
            <td align="CENTER" bgcolor="#ccffff"><font face="Arial" size="3">  21</font></td></tr>
            </tbody></table>
                <p></p><hr>
                   <a href="http://najava.hzinfra.hr/hzinfo/default.asp?category=KORISNICI&amp;service=Psvl&amp;SCREEN=2&amp;VL=..211&amp;D1=161203&amp;ot=1&amp;LANG=HR&amp;sv=%3Csvl">&gt;Pregled sastava vlaka</a>
                   <hr><font size="1" face="Arial,Helvetica" color="009FFF">
            <input type="HIDDEN" name="Category" value="KORISNICI">
            <input type="HIDDEN" name="Service" value="PKVL">
            <input type="HIDDEN" name="ot" value="1">
            <input type="HIDDEN" name="VL" value="211">
            <input type="HIDDEN" name="D1" value="161203">
            <input type="HIDDEN" name="SCREEN" value="1">



            </font></form></body></html>
            """;

    static final String overviewNotFound = """

            <!-- saved from url=(0126)http://najava.hzinfra.hr/hzinfo/default.asp?VL=209&D1=161203&Kaj=Trazi++&Category=KORISNICI&Service=PKVL&SCREEN=2&LANG=HR&ot=1 -->
            <html><head><meta http-equiv="Content-Type" content="text/html; charset=windows-1250">

            <title>Unos podataka za pretragu vlakova          </title>
            </head>
            <body>
            <form method="GET" action="http://najava.hzinfra.hr/hzinfo/default.asp?">
            <table align="CENTER">
            <tbody><tr><td><font color="#000000" face="Arial">
            <h3 align="center">Pregled kretanja vlaka - Unos podataka za pretragu vlakova          </h3></font>
            </td></tr></tbody></table>
            <hr><h3 align="center">
            <table border="0" bgcolor="#ccffff" align="center" face="Arial">
                <tbody><tr style="height:40px;">
                    <td colspan="3" bgcolor="#C0C0C0"><font color="#000000" face="Arial"><h3>&nbsp;Unos podataka za pretragu vlakova          </h3></font></td>
                </tr>
                <tr style="height:30px;">
                    <td><font face="Arial" style="width:210px;height:25px;font-size:18px;">&nbsp;Broj vlaka    &nbsp; </font></td>
                    <td><font face="Arial" style="width:40px;height:20px;font-size:18px;">
                <input class="number" name="VL" type="number" size="5" maxlength="5" required="true" value="" &nbsp;<="" td="">
                    </font></td><td><font face="Arial" style="width:300px;height:30px;">&nbsp;&nbsp;Vlak nije u evidenciji ili nije va?     </font></td>
                 </tr><tr style="height:30px;">
                     <td><font face="Arial" style="width:210px;height:25px;font-size:18px;">&nbsp;Od datuma (GGMMDD)&nbsp; </font></td>
                     <td><font face="Arial" style="width:45px;height:20px;font-size:18px;">
            <input class="number" name="D1" type="number" size="6" maxlength="6" max="&lt;today&gt;" min="130101" required="" aria-required="true" value="&lt;brdt&gt;" &nbsp;<="" td="">
                     </font></td><td><font face="Arial" style="width:300px;height:30px;">&nbsp;&nbsp;                                                            </font></td>
                </tr>
                <tr style="height:40px;">
                    <td colspan="3" bgcolor="#C0C0C0">
                      <p>&nbsp;<input type="submit" name="Kaj" style="width:150px;height:30px;font-size:18px;" value="Trazi  "></p>
                </td></tr>
            </tbody></table>
            <input type="HIDDEN" name="Category" value="KORISNICI">
            <input type="HIDDEN" name="Service" value="PKVL">
            <input type="HIDDEN" name="SCREEN" value="2">
            <input type="HIDDEN" name="LANG" value="HR">
            <input type="HIDDEN" name="ot" value="1">



            </h3></form></body></html>
            """;

}
