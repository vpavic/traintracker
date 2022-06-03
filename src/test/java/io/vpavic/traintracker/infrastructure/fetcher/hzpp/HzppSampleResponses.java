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

}
