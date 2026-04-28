import csv
import random
import re
import uuid
import time
from datetime import date, timedelta

TIPOS = ["DEBITO", "CREDITO"]
TOTAL = 1_000_000
LOG_INTERVAL = 100_000  # loga a cada 100k registros
SEED_FILE = "infra/02-seed.sql"
OUTPUT_FILE = "lancamentos.csv"

# Extrai todos os numero_conta do arquivo seed
print(f"Lendo contas do seed: {SEED_FILE}...")
contas = []
pattern = re.compile(r"VALUES\s*\('[^']+',\s*'([^']+)',\s*'ATIVO'\)")

with open(SEED_FILE, "r", encoding="utf-8") as f:
    for line in f:
        m = pattern.search(line)
        if m:
            numero_conta = m.group(1)
            # ignora a linha de teste com valor literal 'numero_conta'
            if numero_conta != "numero_conta":
                contas.append(numero_conta)

print(f"✅ {len(contas)} contas carregadas do seed.\n")

if not contas:
    raise ValueError("Nenhuma conta encontrada no seed. Verifique o caminho do arquivo.")

print(f"Iniciando geração de {TOTAL:,} lançamentos...")
start_time = time.time()

with open(OUTPUT_FILE, "w", newline="") as f:
    writer = csv.writer(f)
    writer.writerow(["id_lancamento", "numero_conta", "tipo", "valor", "data_lancamento"])

    for i in range(1, TOTAL + 1):
        writer.writerow([
            str(uuid.uuid4()),
            random.choice(contas),
            random.choice(TIPOS),
            round(random.uniform(10.00, 5000.00), 2),
            date.today().isoformat()
        ])

        if i % LOG_INTERVAL == 0:
            elapsed = time.time() - start_time
            pct = i / TOTAL * 100
            rate = i / elapsed
            remaining = (TOTAL - i) / rate
            eta = timedelta(seconds=int(remaining))
            print(f"  [{pct:5.1f}%] {i:>12,} / {TOTAL:,} gerados | "
                  f"{rate:,.0f} reg/s | ETA: {eta}")

elapsed_total = time.time() - start_time
print(f"\n✅ {TOTAL:,} lançamentos gerados em {OUTPUT_FILE}")
print(f"   Tempo total: {timedelta(seconds=int(elapsed_total))}")