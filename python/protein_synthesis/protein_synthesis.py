CODON_DICT = {"GCU": "Ala", "GCC": "Ala", "GCA": "Ala", "GCG": "Ala", "UUA": "Leu", "UUG": "Leu", "CUU": "Leu",
               "CUC": "Leu", "CUA": "Leu", "CUG": "Leu", "CGU": "Arg", "CGC": "Arg", "CGA": "Arg", "CGG": "Arg",
               "AGA": "Arg", "AGG": "Arg", "AAA": "Lys", "AAG": "Lys", "AAU": "Asn", "AAC": "Asn", "AUG": "Met",
               "GAU": "Asp", "GAC": "Asp", "UUU": "Phe", "UUC": "Phe", "UGU": "Cys", "UGC": "Cys", "CCU": "Pro",
               "CCC": "Pro", "CCA": "Pro", "CCG": "Pro", "CAA": "Gln", "CAG": "Gln", "UCU": "Ser", "UCC": "Ser",
               "UCA": "Ser", "UCG": "Ser", "AGU": "Ser", "AGC": "Ser", "GAA": "Glu", "GAG": "Glu", "ACU": "Thr",
               "ACC": "Thr", "ACA": "Thr", "ACG": "Thr", "GGU": "Gly", "GGC": "Gly", "GGA": "Gly", "GGG": "Gly",
               "UGG": "Trp", "CAU": "His", "CAC": "His", "UAU": "Tyr", "UAC": "Tyr", "AUU": "Ile", "AUC": "Ile",
               "AUA": "Ile", "GUU": "Val", "GUC": "Val", "GUA": "Val", "GUG": "Val", "UAG": "Stop", "UGA": "Stop",
               "UAA": "Stop"}


def protein_synthesis(dna: str):
    rna_complement = str.maketrans("ACGT", "UGCA")
    rna = [dna[i:i+3].translate(rna_complement) for i in range(0, len(dna), 3)]
    protein = ' '.join(CODON_DICT[codon] for codon in rna if codon in CODON_DICT)
    return ' '.join(rna), protein


def protein_synthesis2(dna: str):
    rna_complement = str.maketrans("ACGT", "UGCA")
    rna = []
    protein = []
    for i in range(0, len(dna), 3):
        codon = dna[i:i+3].translate(rna_complement)
        rna.append(codon)
        if codon in CODON_DICT:
            protein.append(CODON_DICT[codon])
    return ' '.join(rna), ' '.join(protein)


print(protein_synthesis("TACAGCTCGCTATGAATC"))
print(protein_synthesis2("TACAGCTCGCTATGAATC"))
print(protein_synthesis("ACGTG"))
print(protein_synthesis2("ACGTG"))