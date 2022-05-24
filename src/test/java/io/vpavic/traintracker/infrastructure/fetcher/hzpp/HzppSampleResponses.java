package io.vpavic.traintracker.infrastructure.fetcher.hzpp;

class HzppSampleResponses {

	static final String currentPositionVoyageInProgress = """
			<HTML>
			<HEAD>
			<TITLE>Trenutna pozicija vlaka</TITLE>
			<meta name="viewport" content="width=device-width, initial-scale=1.0" charset=windows-1250">
			</HEAD>
			<BODY BACKGROUND=Images/slika.jpg><TABLE align="CENTER"><TR>
			<TD><FONT COLOR="#333399"><FONT FACE=Verdana,Arial,Helvetica COLOR="#333399">
			<H3 ALIGN=center>HŽ Infrastruktura<BR>                                  </H3></FONT>
			</TR></TABLE>
			<HR>
			<FORM METHOD="GET" ACTION="http://vred.hzinfra.hr/hzinfo/default.asp?">
			<P ALIGN=CENTER>
			<FONT SIZE=6 FACE=Arial,Helvetica COLOR="#333399">
			<TABLE ALIGN=CENETR WIDTH=110%>
			<TD BGCOLOR=#bbddff><I>Trenutna pozicija<br>vlak: </I>   544 <br>
			Relacija:<br>  >  </strong></TD><TR>
			<TD BGCOLOR=#bbddff><I>Kolodvor: </I><strong>LIPOVLJANI<br> </TD><TR>
			<TD BGCOLOR=#bbddff><I>Odlazak  </I><cr>
			19.05.22. u 14:03 sati</TD><TR>
			<TD><FONT FACE=Arial,Helvetica COLOR=#FF000A>
			<BLINK>Kasni   24 min.                                   </BLINK><BR>
			<FONT SIZE=4 FACE=Verdana,Arial,Helvetica COLOR="#333399">
			Vlak se očekuje<br>u kolodvoru: BANOVA JARUGA     <BR>
			</TD><TR><TD>
			</TD></TABLE><HR><FONT SIZE=1 FACE=Arial,Helvetica COLOR=009FFF>
			Stanje vlaka od 19/05/22   u 14:05   <HR>
			<INPUT TYPE="HIDDEN" NAME="Category" VALUE="hzinfo">
			<INPUT TYPE="HIDDEN" NAME="Service" VALUE="Tpvl">
			<INPUT TYPE="HIDDEN" NAME="SCREEN" VALUE="1">
			<INPUT TYPE="SUBMIT" VALUE="Povrat">
			</FORM>
			</BODY>
			</HTML>
			""";

	static final String currentPositionVoyageEnded = """
			<HTML>
			<HEAD>
			<TITLE>Trenutna pozicija vlaka</TITLE>
			<meta name="viewport" content="width=device-width, initial-scale=1.0" charset=windows-1250">
			</HEAD>
			<BODY BACKGROUND=Images/slika.jpg><TABLE align="CENTER"><TR>
			<TD><FONT COLOR="#333399"><FONT FACE=Verdana,Arial,Helvetica COLOR="#333399">
			<H3 ALIGN=center>HŽ Infrastruktura<BR>                                  </H3></FONT>
			</TR></TABLE>
			<HR>
			<FORM METHOD="GET" ACTION="http://vred.hzinfra.hr/hzinfo/default.asp?">
			<P ALIGN=CENTER>
			<FONT SIZE=6 FACE=Arial,Helvetica COLOR="#333399">
			<TABLE ALIGN=CENETR WIDTH=110%>
			<TD BGCOLOR=#bbddff><I>Trenutna pozicija<br>vlak: </I>   540 <br>
			Relacija:<br>  >  </strong></TD><TR>
			<TD BGCOLOR=#bbddff><I>Kolodvor: </I><strong>ZAGREB+GL.+KOL.<br> </TD><TR>
			<TD BGCOLOR=#bbddff><I>Završio vožnju      </I><cr>
			19.05.22. u 06:54 sati</TD><TR>
			<TD><FONT FACE=Arial,Helvetica COLOR=#FF000A>
			<BLINK>Kasni   10 min.                                   </BLINK><BR>
			<FONT SIZE=4 FACE=Verdana,Arial,Helvetica COLOR="#333399">
			<BR>
			</TD><TR><TD>
			</TD></TABLE><HR><FONT SIZE=1 FACE=Arial,Helvetica COLOR=009FFF>
			Stanje vlaka od 19/05/22   u 13:39   <HR>
			<INPUT TYPE="HIDDEN" NAME="Category" VALUE="hzinfo">
			<INPUT TYPE="HIDDEN" NAME="Service" VALUE="Tpvl">
			<INPUT TYPE="HIDDEN" NAME="SCREEN" VALUE="1">
			<INPUT TYPE="SUBMIT" VALUE="Povrat">
			</FORM>
			</BODY>
			</HTML>
			""";

	static final String currentPositionNotFound = """
			<HTML>
			<HEAD>
			<meta name="viewport" content="width=device-width, initial-scale=1.0" charset=windows-1250">
			<TITLE>Trenutna pozicija putničkog vlaka</TITLE>
			</HEAD>
			<BODY BACKGROUND=Images/slika.jpg><TABLE align="CENTER"><TR>
			<TD><FONT COLOR="#333399"><FONT FACE=Verdana,Arial,Helvetica COLOR="#333399">
			<H5 ALIGN=center>HŽ Infrastruktura<BR>Trenutna pozicija putničkog vlaka</H5></FONT>
			</TR></TABLE>
			<HR>
			<FORM METHOD="GET" ACTION="http://vred.hzinfra.hr/hzinfo/default.asp?"><P><FONT FACE=Arial,Helvetica COLOR="#333399" ALIGN=center  >
			<STRONG>Broj vlaka: </STRONG>
			<INPUT NAME="VL" TYPE="TEXT" SIZE="5" MAXLENGTH="5">
			<P>
			<P><STRONG>Vlak nije u evidenciji.                                     </STRONG></P>
			<INPUT TYPE="HIDDEN" NAME="Category" VALUE="hzinfo">
			<INPUT TYPE="HIDDEN" NAME="Service" VALUE="Tpvl">
			<INPUT TYPE="HIDDEN" NAME="SCREEN" VALUE="2">
			<INPUT TYPE="SUBMIT" VALUE=" OK ">
			</FORM>
			<PRE><P>
			<STRONG><P>
			<STRONG><P>
			<STRONG><P></PRE>
			</BODY>
			</HTML>
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
