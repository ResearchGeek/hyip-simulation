hyip-simulation
===============

[![Build Status](https://drone.io/github.com/ResearchGeek/hyip-simulation/status.png)](https://drone.io/github.com/ResearchGeek/hyip-simulation/latest)

#### High Yield Investment Program multi-agent simulation

A high-yield investment program (HYIP) is a type of Ponzi scheme, an investment scam that promises unsustainably high return on investment by paying previous investors with the money invested by new investors. Most of these scams work from anonymous offshore bases which make them hard to track down.

#### Acknowledgements

Simulation was creating using parts of 'Credibility Game' (Paulina Adamska, PJWSTK 2013) and 2nd version of 'Credibility Game' project (Adamska, Kowalik, PJWSTK 2014). Plus some of the HYIP code creating in cooperation with Grzegorz Kowalik (PJWSTK, 2014).

#### Licence

For code licence, please check LICENSE file.

The code is created to work with Repast Simphony 2.2 framework.

Repast License: The Repast suite software and documentation is licensed under a "New BSD" style license. Please note that Repast Simphony uses a variety of tools and third party external libraries each having its own compatible license, including software released under the Eclipse Public License, the Common Public License, the GNU Library General Public License and other licenses.

#### Simulation description

In the simulator we use a simulation model which would indicate the most effective strategies for HYIP owners to maximize their profit when running away. It also investigates the influence of investing in proper script and advertisement on HYIP’s financial results. The model is, to a large extent, based on real-life data, acquired from HYIP monitors and forums.

![Simulation ecosystem](https://dl.dropboxusercontent.com/u/103068909/hyip-ecosystem.png "Simulation ecosystem")

##### Simulation workflow

To investigate the phenomena of HYIPs (especially to analyse the strategies that HYIP owner can take to decide when run away with money), we designed a simulation which contains elements of evolution. After every generation of HYIPs, the effectiveness in terms of increasing balance account is verified and the best strategies are inherited by next generation.

![Workflow](https://dl.dropboxusercontent.com/u/103068909/hyip-sim-workflow.png "Workflow")

##### Dictionary (presenting most important actors, producers and artefacts)

*Investor* - A person (agent) considering investing his money into HYIPs

*HYIP* - High Yield Investment Program (producer)

*Hyip offer* - An investment offered by a HYIP, when accepted an agreement between the Investor and the HYIP

*Investment* - An ongoing investment, characterized by: duration (in days), interest (return in percentage)

*Exit strategy* - Heuristics which HYIPs assess to choose whether close themselve (run aways) or not

**Parameters** (most important are presented below)

*generation_number* - Number of generations

*iteration_number* - Number of iterations in a generation

*inv_0* - Count of investors (part 1)

*inv_1* - Count of investors (part 2)

*inv_2* - Count of investors (part 3)

*mutate_chance* - Chances for enabling/disabling part of the exit-strategy

*mutate_factor* - By how much all exit-strategy values change after an iteration

*hyip_population_size* - Number of HYIPs in a simulation

#### Outputs

hyip_*.csv outputs results, state of every HYIP per every tick
hyip_*params.csv outputs params used

simulation_logs.txt outputs diagnostic data

/batch directory holds typical scenarios with proper parameters

##### Column description

*run* - repast simulation run number

*tick* - repast simulation tick number

*generation* - SUS generation number

*iteration* - SUS iteration number of the current generation

*cash* - HYIP account balance

*Runned_Away* - tells whether HYIP is active or inactive (closed)

*Id* - HYIP id number

*Advert*

*Income* - income after last tick

*Count_Investments* - number of open investments

*Good_Looking*

*Total_Number_Of_Invests* - how many times somebody invested in this HYIP and got payed

*Use_Cash* - is balance used on consideration on running away

*Strategy_Cash* - the amount for above

*Use_Income* - is income used on consideration on running away

*Strategy_Income* - the value for above

*Use_Investor_Count* - is number of investments made used on consideration on running away

*Strategy_Investor_Count* - the value for above

*Use_Time* - is HYIP age used on consideration on running away

*Strategy_Time* - the value for above

*E_Use*

*P_Use*

*Strategy* - string description of this HYIP-s exit strategy
