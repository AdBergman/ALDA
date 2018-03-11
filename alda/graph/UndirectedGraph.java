// Ändra inte på paketet
package alda.graph;

public interface UndirectedGraph<T> {

	// ToDo: metod för att räkna den totala vikten

	/**
	 * Antalet noder i grafen.
	 * 
	 * @return antalet noder i grafen.
	 */
	int getNumberOfNodes();

	/**
	 * Antalet bågar i grafen.
	 * 
	 * @return antalet bågar i grafen.
	 */
	int getNumberOfEdges();

	/**
	 * Lägger till en ny nod i grafen.
	 * 
	 * @param newNode
	 *            datat för den nya noden som ska läggas till i grafen.
	 * @return false om noden redan finns.
	 */
	boolean add(T newNode);

	/**
	 * Kopplar samman tvä noder i grafen. Eftersom grafen är oriktad så spelar
	 * det ingen roll vilken av noderna som står först. Det är också
	 * fullständigt okej att koppla ihop en nod med sig själv. Däremot tillåts
	 * inte multigrafer. Om två noder kopplas ihop som redan är ihopkopplade
	 * uppdateras bara deras kostnadsfunktion.
	 * 
	 * @param node1
	 *            den ena noden.
	 * @param node2
	 *            den andra noden.
	 * @return true om bägge noderna finns i grafen och kan kopplas ihop.
	 */
	boolean connect(T node1, T node2);
	boolean connectNodes(UndirectedGraphNode<T> nodeObj1, UndirectedGraphNode<T> nodeObj2);

	/**
	 * Berättar om två noder är sammanbundan av en båge eller inte.
	 * 
	 * @param node1
	 *            den ena noden.
	 * @param node2
	 *            den andra noden.
	 * @return om noderna är sammanbundna eller inte.
	 */
	boolean isConnected(T node1, T node2);

	/**
	 * Gör en bredden-först-sökning efter en väg mellan två noder.
	 * 
	 * <p>Metoden tar in två parametrar av typ E och hämtar ut korresponderande noder ur grafen via <code>getNode</code>.
	 * Först kontrolleras att start- och slutnod inte är samma person/nod, om så är fallet returneras Kevin Bacon-nummer 0.</p>
	 * <b>Tre <code>HashSet</code> med noder skapas:</b>
	 * <ul>
	 * <li><code>toSearch</code> som innehåller noderna som skall sökas igenom detta varv vilket initialiseras som <code>startNode.getConnectedNodes</code></li>
	 * <li><code>tempSet</code> som kommer att innehålla de noder som är connected till toSearch-noderna</li>
	 * <li><code>allCheckedNodes</code>vilket innehåller alla noder som tidigare har sökts igenom</li>
	 *</ul>
	 * <p>En <code>boolean</code> found sätts till false och kontrollerar en while-loop.
	 * Först i loopen kontrolleras om toSearch-setet innehåller endNode och om den hittas sätts found till true och avbryter <code>while-loopen</code>.</p>
	 * <p>Om den ej innehåller endNode så itererar vi igenom varje nod i toSearch och lägger till dess connectedNodes till tempSet.
	 *
	 * Vidare läggs <code>toSearch </code> till i allCheckedNodes då dessa har kontrollerats. Efter det tas de noder i <code>allCheckedNodes</code> bort från <code>tempSet</code> för att undvika att vi kontrollerar samma nod flera gånger.
	 * Sedan sätts <code>toSearch </code> till <code>tempSet</code> då detta är de noder som skall genomsökas i nästa iteration och <code>tempSet</code> initieras som ett tomt set för användning i nästa iteration.
	 * Efter varje iteration utan att ha hittat en länk till slutnoden så ökas baconNumber med ett.</p>
	 * <p>För att förhindra att fastna i en evig loop om ingen koppling hittas eller för lång körning initieras en timer i början av metoden. Denna styrs via en if-sats som förhindrar körningen att köras längre än 30 sekunder och returnerar då -1.
	 * Detta plockas upp i <code>Kevin6Degree</code> och skriver ut ett felmeddelande.</p>
	 *
	 *
	 * @param start
	 *            startnoden.
	 * @param end
	 *            slutnoden.
	 * @return baconNumber - en int som representerar antalet bågar mellan start- och slutnod.
	 *
	 */
	int breadthFirstSearch(T start, T end);

}
