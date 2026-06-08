INSERT INTO tier_benefit (tier_id, benefit_id)

-- SILVER -> FREE DELIVERY
SELECT t.id, b.id
FROM tiers t, benefits b
WHERE t.name = 'SILVER'
AND b.name = 'Free Delivery';

INSERT INTO tier_benefit (tier_id, benefit_id)

-- GOLD -> FREE DELIVERY
SELECT t.id, b.id
FROM tiers t, benefits b
WHERE t.name = 'GOLD'
AND b.name = 'Free Delivery';

INSERT INTO tier_benefit (tier_id, benefit_id)

-- GOLD -> 10% DISCOUNT
SELECT t.id, b.id
FROM tiers t, benefits b
WHERE t.name = 'GOLD'
AND b.name = 'Gold Discount';

INSERT INTO tier_benefit (tier_id, benefit_id)

-- PLATINUM -> FREE DELIVERY
SELECT t.id, b.id
FROM tiers t, benefits b
WHERE t.name = 'PLATINUM'
AND b.name = 'Free Delivery';

INSERT INTO tier_benefit (tier_id, benefit_id)

-- PLATINUM -> 15% DISCOUNT
SELECT t.id, b.id
FROM tiers t, benefits b
WHERE t.name = 'PLATINUM'
AND b.name = 'Platinum Discount';

INSERT INTO tier_benefit (tier_id, benefit_id)

-- PLATINUM -> PRIORITY SUPPORT
SELECT t.id, b.id
FROM tiers t, benefits b
WHERE t.name = 'PLATINUM'
AND b.name = 'Priority Support';

INSERT INTO tier_benefit (tier_id, benefit_id)

-- PLATINUM -> EARLY ACCESS
SELECT t.id, b.id
FROM tiers t, benefits b
WHERE t.name = 'PLATINUM'
AND b.name = 'Early Access';