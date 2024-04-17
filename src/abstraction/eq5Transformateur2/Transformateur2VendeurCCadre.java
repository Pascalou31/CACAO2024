package abstraction.eq5Transformateur2;

import java.util.List;

import abstraction.eqXRomu.contratsCadres.Echeancier;
import abstraction.eqXRomu.contratsCadres.ExemplaireContratCadre;
import abstraction.eqXRomu.contratsCadres.IVendeurContratCadre;
import abstraction.eqXRomu.filiere.Filiere;
import abstraction.eqXRomu.produits.Feve;

import abstraction.eqXRomu.produits.IProduit;

public class Transformateur2VendeurCCadre extends Transformateur2AcheteurCCadre implements IVendeurContratCadre {
	private List<ExemplaireContratCadre> contratsEnCours;

	
	public Transformateur2VendeurCCadre () {
		super();
	}
	
	public void next() {
		super.next();
	}
	
	public boolean vend(IProduit produit) {
		return (produit.getType().equals("Chocolat") && this.getQuantiteEnStock(produit, cryptogramme)>0)
				|| (produit.getType().equals("Feve") && this.getQuantiteEnStock(produit, cryptogramme)>10000); //Valeur à changer
	}

	public Echeancier contrePropositionDuVendeur(ExemplaireContratCadre contrat) {
		if (contrat.getEcheancier().getQuantiteTotale()< totalStocksChoco.getValeur()){
			return contrat.getEcheancier(); //peut être changé
		}else {
			return null;
		}
		}
	
	public double propositionPrix(ExemplaireContratCadre contrat) {
		return contrat.getPrix();
	}

	public double contrePropositionPrixVendeur(ExemplaireContratCadre contrat) {
		if (Filiere.random.nextDouble() < 0.2) { // 20% des cas
	        return contrat.getPrix(); // ne refait pas de contreproposition
	    } else {
	        return contrat.getPrix() * 1.07; // Contreproposition de 7% à la hausse
	    }
	}

	public void notificationNouveauContratCadre(ExemplaireContratCadre contrat) {
		if (contrat.getVendeur().equals(this)) {
			journalCC.ajouter("Nouveau contrat vendeur accepté : "+"#"+contrat.getNumero()+" | Acheteur : "+contrat.getAcheteur()+" | Vendeur : "+contrat.getVendeur()+" | Produit : "+contrat.getProduit()+" | Quantité totale : "+contrat.getQuantiteTotale()+" | Prix : "+contrat.getPrix());	
			this.contratsEnCours.add(contrat);
		} else {
			super.notificationNouveauContratCadre(contrat);
		}
	}

	public double livrer(IProduit produit, double quantite, ExemplaireContratCadre contrat) {
		journalCC.ajouter("Livraison de : "+quantite+", tonnes de :"+produit.getType()+" provenant du contrat : "+contrat.getNumero());
		stockFeves.put((Feve)produit, stockFeves.get((Feve)produit)-quantite);
		totalStocksFeves.retirer(this, quantite, cryptogramme);
		return quantite;
		}

}
