-----------------------------------------------------------------------------------------
-- update	24.02.2018 convert to spring 5
-----------------------------------------------------------------------------------------
UPDATE users 
SET password = '{bcrypt}$2a$13$GwPQNWAfenYSb06qxu/Nqevmwe31I4FJreraz34ScjbpAUBnO0S4y'
WHERE id = 1;

UPDATE users 
SET password = '{bcrypt}$2a$13$g.kS1DuAeebMpouKuRTKZesauQlqQbcEgNSZqUvFkr21ubl3d26Qi'
WHERE id = 3;

UPDATE users 
SET password = '{bcrypt}$2a$12$Ljya1s3.uWu7svzYEgMT3OUpsMLSwYTgybCID.e/ViVc2x8XM038W'
WHERE id = 4;

UPDATE users 
SET password = '{bcrypt}$2a$12$ZuV6/OziRcAHJtbqr0cvmOMkae.pgjbSfayFpL5WqkhfmIw.4F17m'
WHERE id = 5;

UPDATE users 
SET password = '{bcrypt}$2a$12$P1tBOtsZNn7ghpLZRIY3Ae7FnIORN9gq.4BY/7vzes04usHVnPhMi'
WHERE id = 6;
