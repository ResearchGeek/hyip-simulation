hyip-simulation
===============

**High Yield Investment Program multi-agent simulation**

A high-yield investment program (HYIP) is a type of Ponzi scheme, an investment scam that promises unsustainably high return on investment by paying previous investors with the money invested by new investors. Most of these scams work from anonymous offshore bases which make them hard to track down.

**Acknowledgments**

Simulation was creating using parts of 'Credibility Game' (Paulina Adamska, PJWSTK 2013) and 2nd version of 'Credibility Game' project (Adamska, Kowalik, PJWSTK 2014). Plus some of the HYIP code creating in cooperation with Grzegorz Kowalik (PJWSTK, 2014).

**Licence**

For code licence, please check LICENSE file.

The code is created to work with Repast Simphony 2.1 framework.

Repast License: The Repast suite software and documentation is licensed under a "New BSD" style license. Please note that Repast Simphony uses a variety of tools and third party external libraries each having its own compatible license, including software released under the Eclipse Public License, the Common Public License, the GNU Library General Public License and other licenses.

**Dictionary**

*Investor* - a person investing his money into Hyip-s

*Hyip* - High Yield Investment Program

*Hyip offer* - an investment offered by the Hyip, agrement between investor and hyip

*Investment* - an ongoing investment

**Parameters**

*generation_number* - number of generations

*iteration_number* - number of iterations in a generation

*inv_0* - count of investors in part 1

*inv_1* - count of investors in part 2

*inv_2* - count of investors in part 3

*income_eval_frompeek* - be default "false", tells how we want to assess next payouts

*hyip_population_size* - number of hyips in a simulation

**Outputs**

hyip_*.csv outputs results, state of every hyip per every tick
hyip_*params.csv outputs params used

simulation_logs.txt outputs diagnostic data

/batch directory holds typical scenarios with proper parameters

**Column description**

*run* - repast simulation run number

*tick* - repast simulation tick number

*generation* - SUS generation number

*iteration* - SUS iteration number of the current generation

*cash* - hyip account balance

*hyip* - hyip id
