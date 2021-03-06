Для очистки DNS-кэша и доступа к доменному имени, после деплоя рекомендуется выполнить:

```bash
sudo systemd-resolve --statistics
```

```
DNSSEC supported by current servers: no

Transactions               
Current Transactions: 0    
  Total Transactions: 69417
                           
Cache                      
  Current Cache Size: 285  
          Cache Hits: 1023 
        Cache Misses: 1329 
                           
DNSSEC Verdicts            
              Secure: 0    
            Insecure: 0    
               Bogus: 0    
       Indeterminate: 0    
```

```bash
sudo systemd-resolve --flush-caches
sudo systemd-resolve --statistics
```

```
DNSSEC supported by current servers: no

Transactions               
Current Transactions: 0    
  Total Transactions: 69443
                           
Cache                      
  Current Cache Size: 0    
          Cache Hits: 1039 
        Cache Misses: 1339 
                           
DNSSEC Verdicts            
              Secure: 0    
            Insecure: 0    
               Bogus: 0    
       Indeterminate: 0    
```
