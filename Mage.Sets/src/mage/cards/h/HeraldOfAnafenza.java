/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.OutlastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.WarriorToken;
import mage.game.stack.StackAbility;

/**
 *
 * @author LevelX2
 */
public class HeraldOfAnafenza extends CardImpl {

    public HeraldOfAnafenza(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add("Human");
        this.subtype.add("Soldier");

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Outlast {2}{W} <em>({2}{W} {T}: Put a +1/+1 counter on this creature.  Outlast only as a sorcery.)</em>
        this.addAbility(new OutlastAbility(new ManaCostsImpl("{2}{W}")));

        // Whenever you activate Herald of Anafenza's outlast ability, create a 1/1 white Warrior creature token.
        this.addAbility(new HeraldOfAnafenzaTriggeredAbility());

    }

    public HeraldOfAnafenza(final HeraldOfAnafenza card) {
        super(card);
    }

    @Override
    public HeraldOfAnafenza copy() {
        return new HeraldOfAnafenza(this);
    }
}

class HeraldOfAnafenzaTriggeredAbility extends TriggeredAbilityImpl {

    public HeraldOfAnafenzaTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(new WarriorToken()), false);
    }

    public HeraldOfAnafenzaTriggeredAbility(final HeraldOfAnafenzaTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HeraldOfAnafenzaTriggeredAbility copy() {
        return new HeraldOfAnafenzaTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ACTIVATED_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getSourceId().equals(getSourceId())) {
            StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getTargetId());
            if (stackAbility != null && (stackAbility.getStackAbility() instanceof OutlastAbility)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you activate {this}'s outlast ability, " + super.getRule();
    }
}
