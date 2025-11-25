package sayTheSpire.ui.effects;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.HashSet;
import sayTheSpire.ui.effects.handlers.*;

/**
 * This manages effects and mod related triggers for them.
 */
public class EffectManager {

    private HashMap<Class<? extends AbstractGameEffect>, Class<? extends EffectHandler>> registeredHandlers;
    private HashMap<AbstractGameEffect, EffectHandler> registeredEffects;
    private HashSet<AbstractGameEffect> effects, topLevelEffects, topLevelEffectsQueue, effectsQueue;

    public EffectManager() {
        this.registeredHandlers = new HashMap();
        this.registeredEffects = new HashMap();
        this.effects = new HashSet();
        this.topLevelEffects = new HashSet();
        this.topLevelEffectsQueue = new HashSet();
        this.effectsQueue = new HashSet();
        this.registerHandlers();
    }

    private void addEffect(HashSet<AbstractGameEffect> targetSet, AbstractGameEffect effect) {
        if (this.registeredEffects.containsKey(effect))
            return;
        targetSet.add(effect);
        EffectHandler handler = this.getHandlerForEffect(effect);
        if (handler != null) {
            this.registeredEffects.put(effect, handler);
            handler.onAdd();
        }
    }

    private EffectHandler getHandlerForEffect(AbstractGameEffect effect) {
        Class<? extends EffectHandler> handlerClass = this.registeredHandlers.getOrDefault(effect.getClass(), null);
        if (handlerClass == null)
            return null;
        try {
            Constructor ctor = handlerClass.getDeclaredConstructor(AbstractGameEffect.class);
            EffectHandler handler = (EffectHandler) ctor.newInstance(effect);
            return handler;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Registers an effect handler for the given effect type and descendents of that type.
     * 
     * @param effectClass
     *            The class of the game effect to use
     * @param handlerClass
     *            The handler to be used for effects of that type
     */
    public void registerEffectHandler(Class<? extends AbstractGameEffect> effectClass,
            Class<? extends EffectHandler> handlerClass) {
        this.registeredHandlers.put(effectClass, handlerClass);
    }

    private void registerHandlers() {
        CardManipulationEffectHandler.registerHandlers(this);
        ObtainKeyEffectHandler.registerHandlers(this);
    }

    private void removeEffect(HashSet<AbstractGameEffect> targetSet, AbstractGameEffect effect) {
        targetSet.remove(effect);
        EffectHandler handler = this.registeredEffects.getOrDefault(effect, null);
        if (handler != null) {
            this.registeredEffects.remove(effect);
            handler.onRemove();
        }
    }

    private void updateDiffs(HashSet<AbstractGameEffect> managedSet, HashSet<AbstractGameEffect> gameSet) {
        HashSet<AbstractGameEffect> effectList = new HashSet(managedSet);
        effectList.addAll(gameSet);
        for (AbstractGameEffect effect : effectList) {
            if (gameSet.contains(effect) && !managedSet.contains(effect)) {
                this.addEffect(managedSet, effect);
            } else if (!gameSet.contains(effect) && managedSet.contains(effect)) {
                this.removeEffect(managedSet, effect);
            }
        }
    }

    /**
     * Handles updating of effects and handlers.
     */
    public void update() {
        this.updateDiffs(this.effects, new HashSet<AbstractGameEffect>(AbstractDungeon.effectList));
        this.updateDiffs(this.topLevelEffects, new HashSet(AbstractDungeon.topLevelEffects));
        this.updateDiffs(this.topLevelEffectsQueue, new HashSet(AbstractDungeon.topLevelEffectsQueue));
        this.updateDiffs(this.effectsQueue, new HashSet(AbstractDungeon.effectsQueue));
        for (EffectHandler handler : this.registeredEffects.values()) {
            handler.update();
        }
    }
}
