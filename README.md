Carthage
========
A Minecraft 1.7.2 mod.

Carthage adds two new blocks:
-Trading Post
--Used to set up trades between players. Uses a ledger to define what items are available for trade. Has a primary inventory
that holds the offered item.

-Register
--Consumes paper to create Ledgers. Ledgers define the value of items in a trade. If the player is looking for Coal,
he would add it to his ledger and assign it a Credit value.

To trade, the player places a Trading Post and creates a Ledger with the Register. For example:
Player A places a Trading Post. He has an abundance of coal, so he sets the Trading Post to offer his coal. He fills
its inventory up with all of his stacks, and assigns a Credit value of 10 per coal.

Then Player A goes to his Register, puts in a piece of paper, and then has the option of defining four trade-able items
for this ledger. He needs Apples and Oak Saplings, so he places each of those in the ledger and gives them each a credit
value of 2 per. Afterward, he takes the ledger and puts it in the Trading Post.

Later that day, Player B comes upon Player A's trading post. He sees that Player A is offering coal, and Player B really
some. He sees that Player A is accepting Apples, which he has a lot of, so he uses the Trading post, and in the trade
section puts in 60 apples. The UI tells him he has 120 credits now, and that each coal is 10 credits - clicking the coal,
his 60 Apples are put into the Trading Post's private storage, and Player B is given 12 of the coal from the Trading Post's
inventory.
