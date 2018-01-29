# Docx4jUFPI

<i>Docx4jUFPI is an open source library for generating reports in docx, using the 
	<a href="https://www.docx4java.org/trac/docx4j" target="_blank">Docx4j</a> library.
</i>

<hr>

# Getting Started

<p> Download jar file <a href="https://github.com/renatofelixdev/Docx4jUFPI/blob/master/target/Docx4jUFPI-0.0.1-SNAPSHOT.jar"> Docx4jUFPI-0.0.1-SNAPSHOT.jar </a>. </p>
<p> Import jar file into project and add dependency in your pom.xml:</p>

```html
	<dependency>
	    <groupId>ufpi.br</groupId>
	    <artifactId>Docx4jUFPI</artifactId>
	    <version>0.0.1-SNAPSHOT</version>
	    <scope>system</scope>
	    <systemPath>${project.basedir}/src/main/resources/Docx4jUFPI-0.0.1-SNAPSHOT.jar</systemPath>
	    <type>jar</type>
	</dependency>
```

<hr>

# Documentation

Main Classes: 

> <b>ItemSecao:</b> This class is responsable for generate sections in word document;<br>
> <b>ItemSumario:</b> This class is responsable for generate sumary in word document;<br>
> <b>ItemParagrafo:</b> This class is responsable for generate paragraphs and custom;<br>
> <b>ItemImagem:</b> This class is responsable for import image in document;<br>
> <b>ItemTabela:</b> This class is responsable for generate tables (together with ItemLinha and ItemColuna);<br>
> <b>ItemLinha:</b> This class is resposable for insert rows in table;<br>
> <b>ItemColuna:</b> This class is responsable for insert cells in rows;<br>
> <b>ItemLista:</b> This class is responsable for generate paragraph with marks and numerations;<br>
> <b>ItemQuebraPagina:</b> This class is responsable for insert page break in document;<br>
> <b>ItemContainer:</b> This class is a neutral element for agroup others elements;<br>

<hr>
