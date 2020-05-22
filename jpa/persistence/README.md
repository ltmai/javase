# Persistence with JPA 

This is an example of entity relationship in JPA.

## One-To-Many relationship

### Mapping to non-entity object

Some one-to-many relationships can be represented by a mapping. Let's take journal as an example. 
Each journal entry consists of a message with place-holders for parameter values. In the database  
the journal entries and their parameters are modeled by table Journal and JournalParam. From the
JournalParam entity we only need to know the positin and the corresponding value, other attributes
are not interesting for us. This simple relationship can be represented a mapping from position to
parameter value as follows: 

```java
@ElementCollection
@CollectionTable(name = "JournalParam", joinColumns = {@JoinColumn(name = "JOURNAL_ID", referencedColumnName = "ID")})
@MapKeyColumn(name = "POSITION")
@Column(name = "PARAMETER")
private Map<Integer, String> parameters;
```

### Mapping to entity object

If the many-entity contains other attributes, the relationship can be represented as a mapping to
the many-entity itself as in the following example. Each order may contains many items, each item
may contain attributes like article, amount, price, etc: 

```java
@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
@MapKeyColumn(name = "POSITION", nullable = false, insertable = false, updatable = false)
@JoinColumn(name = "ORDER_ID", nullable = false, insertable = false, updatable = false, referencedColumnName = "ID")
private Map<Long, Item> items = new HashMap<Long, Item>();
```